package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class Diagram(entityType:String,content:String,entityId:Option[Int],id:Option[Int])

class Diagrams(tag:Tag)extends Table[Diagram](tag,"t_diagrams"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def entityType = column[String]("entityType")
  def content = column[String]("content",O.SqlType("longtext"))
  def entityId = column[Int]("entityId")
  def * = (entityType,content,entityId.?,id.?)<>(Diagram.tupled,Diagram.unapply)
}

object Diagrams extends TableQuery(new Diagrams((_))){}

class DiagramRepo{
  val tableQuery = Diagrams
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val getDiagramResult = GetResult(r => Diagram(r.nextString(),r.nextString(),r.nextIntOption(),r.nextIntOption()))
  //  def clobToString(t: Clob): String = t.getSubString(1, t.length.asInstanceOf[Int])
  //  def stringToClob(dt: String): Clob = new SerialClob(dt.toCharArray)
  //
  //  implicit val clobFormat = new Format[Clob]{
  //    def writes(t: Clob): JsValue = toJson(clobToString(t))
  //    def reads(json: JsValue): JsResult[Clob] = fromJson[String](json).map(stringToClob)
  //  }
  implicit val diagramFormat = Json.format[Diagram]

  lazy val dbUtil = new DatabaseUtil()
  implicit val diagramValidator:Validator[Diagram] = validator[Diagram]{ diagram =>
    diagram.entityId is notEmpty
  }

  def createTable = {
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def getMenuDiagramContent() :Mono[String]={
    val action = tableQuery.filter(_.entityType === "menu").map(_.content).result.head
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def updateMenuDiagramContent(diagram:String) = {
    val head = tableQuery.filter(_.entityType === "menu").result.headOption
    val action = head.flatMap{
      case None => tableQuery += Diagram("menu",diagram,Option.empty,Option.empty)
      case _ => tableQuery.filter(_.entityType === "menu").map(_.content).update(diagram)
    }
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }
}



