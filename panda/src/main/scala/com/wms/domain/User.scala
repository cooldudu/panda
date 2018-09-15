package com.wms.domain

import java.sql.Date

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import com.wms.core.utils.common.ObjectUtils
import org.springframework.data.domain.Pageable
import play.api.libs.json._
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class User(createDate:Date,modifyDate:Date,roleNames:String,userName:String,accountId:Int,id:Option[Int]=None)

class Users(tag: Tag) extends Table[User](tag,"t_users"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def createDate = column[Date]("createDate",O.Default(new Date(new java.util.Date().getTime)))
  def modifyDate = column[Date]("modifyDate",O.Default(new Date(new java.util.Date().getTime)))
  def roleNames = column[String]("roleNames")
  def userName = column[String]("userName")
  def accountId = column[Int]("account_id")
  def * = (createDate,modifyDate,roleNames,userName,accountId,id.?)<>(User.tupled, User.unapply)
  def account = foreignKey("account_fk",accountId,Accounts)(_.id,onDelete=ForeignKeyAction.Cascade)
}

object Users extends TableQuery(new Users(_)){
  val findByUserName = this.findBy(_.userName)
}

class UserRepo{
  val tableQuery = Users

  import scala.compat.java8.FutureConverters._
  implicit val getUserResult = GetResult(r => User(r.nextDate(), r.nextDate(), r.nextString(), r.nextString(), r.nextInt(), r.nextIntOption()))
  implicit val userFormat = Json.format[User]
  lazy val dbUtil = new DatabaseUtil()
  implicit val userValidator:Validator[User] = validator[User]{user =>
    user.userName is notEmpty
    user.accountId must be >0
  }

  def createTable={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findTotalNum(userName:String):Mono[Int] = {
    val action = tableQuery.filter(user => {
      if(ObjectUtils.isNotEmpty(userName)){
        user.userName like "%"+userName+"%"
      }else{
        LiteralColumn(true)
      }
    })
    Mono.fromFuture(dbUtil.getFuture(action.length.result).toJava.toCompletableFuture)
  }

  def findByPageable(userName:String,page:Pageable):Flux[User] = {
    val action = tableQuery.filter(user => {
      if(ObjectUtils.isNotEmpty(userName)){
        user.userName like "%"+userName+"%"
      }else{
        LiteralColumn(true)
      }
    }).drop((page.getPageNumber-1)*page.getPageSize).take(page.getPageSize)
    if (!page.getSort.isUnsorted) {
      val sort = page.getSort.iterator().next()
      if (sort.isAscending) {
        action.sortBy(_.column[String](sort.getProperty).asc.nullsLast)
      } else {
        action.sortBy(_.column[String](sort.getProperty).desc.nullsLast)
      }
    }
    return Flux.from( dbUtil.getStream(action.result))
  }

  def save(userStr:String):Mono[Int]={
    val userObj = Json.parse(userStr).validate[User].fold(
      errors => errors.mkString,
      category => category
    )
    if(userObj.isInstanceOf[User]){
      val user = userObj.asInstanceOf[User]
      if(user.id.getOrElse(0) >0){
        val action = tableQuery.filter(_.id===user.id).update(user)
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }else{
        val action = tableQuery += user
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }
    }else{
      Mono.just(0)
    }
  }

  def delete(id:Int):Mono[Int]={
    val action = tableQuery.filter(_.id===id).delete
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }
}