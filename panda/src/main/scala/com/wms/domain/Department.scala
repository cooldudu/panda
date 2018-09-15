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

case class Department(name:String,level:String,descript:String,companyId:Int,parentId:Option[Int],order:Int,id:Option[Int] = None)

class Departments(tag: Tag)extends Table[Department](tag,"t_depts"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def name = column[String]("name",O.SqlType("varchar(200)"))
  def level = column[String]("level",O.SqlType("varchar(200)"))
  def descript = column[String]("descript")
  def companyId = column[Int]("companyId")
  def parentId = column[Option[Int]]("parentId")
  def order = column[Int]("orderNum",O.Default(1000))
  def * = (name,level,descript,companyId,parentId,order,id.?)<>(Department.tupled,Department.unapply)
}

object Departments extends TableQuery(new Departments(_)){}

class DepartmentRepo{
  val tableQuery = Departments
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._

  implicit  val getDepartmentResult = GetResult(r => Department(r.nextString,r.nextString,
    r.nextString,r.nextInt,r.nextIntOption,r.nextInt,r.nextIntOption))

  implicit val departmentFormat = Json.format[Department]
  lazy val dbUtil = new DatabaseUtil()
  implicit val departmentValidator:Validator[Department] = validator[Department]{department =>
    department.name is notEmpty
    department.level is notEmpty
  }

  def createTable={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findByCompanyId(companyId:Int):Flux[Department]={
    val action = tableQuery.filter(_.companyId === companyId).sortBy(_.order.asc).result
    return Flux.from( dbUtil.getStream(action))
  }

  def findById(id:Int):Mono[Option[Department]] = {
    val action = tableQuery.filter(_.id===id).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def updateDrop(id:Option[Int],parentId:Option[Int],order:Int,level:String) = {
    val action = tableQuery.filter(_.id===id.get).map(dept => (dept.parentId,dept.order,dept.level))
      .update((parentId,order,makeLevel(level)))
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def edit(id:Int,name:String,descript:String):Mono[Int] ={
    val action = tableQuery.filter(_.id===id).map(dept => (dept.name,dept.descript))
      .update((name,descript))
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def setDragInfo(dragId:Int,dropId:Int,dropType:String)={
    val drag = findById(dragId).block()
    val drop = findById(dropId).block()
    if(drag.nonEmpty&&drop.nonEmpty){
      val dragDept = drag.get
      val dropDept = drop.get
      dropType match {
        case "before" =>{
          updateDrop(dragDept.id,dropDept.parentId,dropDept.order-1,dropDept.level.substring(0,dropDept.level.length-4))
        }
        case "after" =>{
          updateDrop(dragDept.id,dropDept.parentId,dropDept.order+1,dropDept.level.substring(0,dropDept.level.length-4))
        }
        case "inner" =>{
          updateDrop(dragDept.id,dropDept.id,10000,dropDept.level)
        }
      }
    }
  }

  def save(department:String):Mono[Int]={
    val deptObj = Json.parse(department).validate[Department].fold(
      errors => errors.mkString,
      category => category
    )
    if(deptObj.isInstanceOf[Department]){
      val dept = deptObj.asInstanceOf[Department]
      if(dept.id.getOrElse(0)>0){
        val action = tableQuery.filter(_.id===dept.id).update(dept)
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }else{
        val action = tableQuery += dept
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }
    }else{
      Mono.just(0)
    }
  }

  def delete(id:Int):Mono[Int] = {
    val action = tableQuery.filter(_.id===id).delete
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def getMaxLevel(parentLevel:String):Mono[Option[String]]={
    val action = tableQuery.filter(_.level like parentLevel+"%").filter(_.level.length===(parentLevel.length+4)).map(_.level).max.result
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def makeLevel(parentLevel:String):String={
    val levelStr = getMaxLevel(parentLevel).block()
    if(levelStr.nonEmpty){
      var level = levelStr.get.toInt
      level = level+1;
      var result = level.toString
      while(result.length%4!=0){
        result = "0"+result
      }
      return result
    }else{
      return parentLevel+"0001"
    }
  }
}