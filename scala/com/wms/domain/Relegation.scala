package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class Relegation(userName:String,deptId:Int,id:Option[Int]=None)

class Relegations(tag: Tag)extends Table[Relegation](tag,"t_relegations"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def userName = column[String]("userName")
  def deptId = column[Int]("dept_id")
  def * =(userName,deptId,id.?)<>(Relegation.tupled,Relegation.unapply)
  def department = foreignKey("rele_dept_fk",deptId,Departments)(_.id,onDelete=ForeignKeyAction.NoAction)
}

object Relegations extends TableQuery(new Relegations(_)){}

class RelegationRepo{
  val tableQuery = Relegations
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._

  implicit val getRelegationResult = GetResult(r => Relegation(r.nextString(),r.nextInt(),r.nextIntOption()))
  implicit val relegationFormat = Json.format[Relegation]
  lazy val dbUtil = new DatabaseUtil()
  implicit val relegationValidator:Validator[Relegation] = validator[Relegation]{relegation =>
    relegation.userName must notEmpty
    relegation.deptId must be >0
  }

  def createTable={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findByAccountId(userName:String) = {
    val action = tableQuery.filter(_.userName === userName).result
    Flux.from( dbUtil.getStream(action))
  }
}
