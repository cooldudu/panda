package com.wms.core.database

import com.wms.config.DataSourceFactory
import slick.basic.DatabasePublisher
import slick.dbio.{DBIOAction, Streaming}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Awaitable, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class DatabaseUtil {

}
object DatabaseUtil {
  import org.slf4j.LoggerFactory

  private val LOGGER = LoggerFactory.getLogger(classOf[DatabaseUtil])
  def getDatabase():Database={
    val db = Database.forDataSource(DataSourceFactory.makeDataSource: javax.sql.DataSource,
      Some(10: Int))
    db
  }

  def getFuture[T](action : DBIO[T]):Future[T] = {
    val db = getDatabase
    db.run(action)
  }

  def execRun[T](action: DBIO[T]):Option[T] = {
    val db = getDatabase
    val f = db.run(action)
    f.onComplete{
      case Success(n) => println(n)
      case Failure(ex) => println(ex.getMessage)
    }
    try {
      Some(Await.result(db.run(action), Duration.Inf))
    }finally  {
      db.close()
    }
  }

  def makeTrasactionalAction[T](action: DBIOAction[T,NoStream,Effect.Transactional]):DBIOAction[String, NoStream, Effect.Transactional with Effect with Effect.Transactional with Effect] = {
    val db = getDatabase
    val rollbackAction = action.flatMap(_ => DBIO.failed(new Exception("Roll it back"))).transactionally
    val errorHandleAction = rollbackAction.asTry.flatMap{
      case Failure(e:Throwable) => DBIO.successful(e.getMessage)
      case Success(_) => DBIO.successful("success")
    }
    errorHandleAction
  }

  def runAndJudgeRollback[T](awaitable : Awaitable[T]):Boolean = {
    Await.result(awaitable,Duration.Inf) == "Roll it back"
  }

  def execStream[T](action: DBIOAction[_, Streaming[T], Nothing],f: Any=>T):Unit = {
    val db = getDatabase
    val fu = db.stream(action).foreach(f)
    fu.onComplete{
      case Success(n) => println(n)
      case Failure(ex) => println(ex.getMessage)
    }
    try {
      Await.result(fu, Duration.Inf)
    }finally db.close()
  }

  def getStream[T](action:DBIOAction[_, Streaming[T], Nothing]):DatabasePublisher[T] = {
    val db = getDatabase
    db.stream(action)
  }
}
