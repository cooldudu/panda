package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import reactor.core.publisher.{Flux, Mono}
import slick.dbio.{DBIOAction, Effect}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._
import slick.sql.{FixedSqlAction, SqlAction}

import scala.language.{higherKinds, postfixOps}

case class Menu(name:String,icon:String,url:Option[String],order:Int,uid:String,parentId:Option[String],id:Option[Int])

class Menus(tag:Tag) extends Table[Menu](tag,"t_menus"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def icon = column[String]("icon")
  def url = column[String]("url")
  def uid = column[String]("uid",O.SqlType("varchar(200)"))
  def order = column[Int]("orderNum")
  def parentId = column[String]("parentId")
  def * = (name,icon,url.?,order,uid,parentId.?,id.?)<>(Menu.tupled,Menu.unapply)
}

object Menus extends TableQuery(new Menus(_)){}

class MenuRepo{
  val tableQuery = Menus
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._

  implicit val getMenuResult = GetResult(r => Menu(r.nextString(),r.nextString(),
    r.nextStringOption(),r.nextInt(),r.nextString(),r.nextStringOption(),r.nextIntOption()))

  implicit val menuFormat = Json.format[Menu]
  lazy val dbUtil = new DatabaseUtil()
  implicit val menuValidator:Validator[Menu] = validator[Menu]{ menu =>
    menu.name is notEmpty
    menu.order must be >0
  }

  def createTable={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findAllMenu : Flux[Menu] = {
    val action = tableQuery.sortBy(_.order.asc.nullsFirst).result
    return Flux.from( dbUtil.getStream(action))
  }

  def createMenu(r: JsValue):Option[SqlAction[Int, NoStream, Effect.Write]] ={
    val isDel = (r \ "isDel").as[Boolean]
    if(!isDel){
      val uid = (r \ "entityId").as[String]
      val menu = Menu(uid,"fa fa-desktop",Option.empty,1,uid,Option.empty,Option.empty)
      val action = tableQuery += menu
      return Some(action)
    }else{
      return Option.empty
    }
  }

  def editMenu(r: JsValue):Option[SqlAction[Int, NoStream, Effect.Write]] ={
    val isDel = (r \ "isDel").as[Boolean]
    if(!isDel){
      val uid = (r \ "entityId").as[String]
      val fieldMap = Map("名称"->"name","图标"->"icon","排序"->"orderNum","URL地址"->"url","类别"->"target")
      val name = fieldMap.get((r \ "name").as[String]).get
      val value = (r \ "value").as[String]
      //val action = sqlu"""update t_menus set ${name} = ${value} where uid= ${uid}"""
      val action = tableQuery.filter(_.uid === uid).map(_.column[String](name)).update(value)
      return Some(action)
    }else{
      return Option.empty
    }
  }

  def parentMenu(r: JsValue):Option[SqlAction[Int, NoStream, Effect.Write]] ={
    val isDel = (r \ "isDel").as[Boolean]
    if(!isDel){
      val miniType = (r \ "miniType").as[String]
      return miniType match{
        case "port" =>{
          val sourceId = (r \ "sourceId").as[String]
          val targetId = (r \ "targetId").as[String]
          Some(tableQuery.filter(_.uid === targetId).map(_.parentId).update(sourceId))
        }
        case "connSource" =>{
          val sourceId = (r \ "targetId").as[String]
          val targetId = (r \ "ownTargetId").as[String]
          Some(tableQuery.filter(_.uid === targetId).map(_.parentId).update(sourceId))
        }
        case "connTarget" =>{
          val sourceId = (r \ "ownSourceId").as[String]
          val targetId = (r \ "targetId").as[String]
          Some(tableQuery.filter(_.uid === targetId).map(_.parentId).update(sourceId))
        }
      }
    }else{
      return Option.empty
    }
  }

  def deleteMenu(r:JsValue):Option[SqlAction[Int, NoStream, Effect.Write]]={
    val isDel = (r \ "isDel").as[Boolean]
    if(!isDel){
      val uid = (r \ "entityId").as[String]
      val action = tableQuery.filter(_.uid === uid).delete
      return Some(action)
    }else{
      return Option.empty
    }
  }

  def saveMenu(result:String):Mono[Unit] = {
    import play.api.libs.json.Json
    val json = Json.parse(result).asInstanceOf[JsArray]
    var actions = Seq[DBIOAction[_, NoStream, Effect.Write]]()
    json.value.foreach(r => {
      val jsonType = (r \ "type").as[String]
      val action = jsonType match {
        case "create" => createMenu(r)
        case "edit" => editMenu(r)
        case "parent" => parentMenu(r)
        case "delete" => deleteMenu(r)
      }
      if(action.nonEmpty){
        actions = actions :+ action.get.asTry
      }
    })
    Mono.fromFuture(dbUtil.getFuture(DBIO.seq(actions:_ *).transactionally).toJava.toCompletableFuture)
  }

  def findByUid(menuUid:String):Mono[Option[Menu]]={
    val action = tableQuery.filter(_.uid === menuUid).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findByName(name:String):Mono[Option[Menu]]={
    val action = tableQuery.filter(_.name === name).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }
}