package com.wms.user.domain

import java.sql.Date

import com.wms.core.database.DatabaseUtil
import slick.jdbc.MySQLProfile.api._
import io.strongtyped.active.slick._
import slick.ast.BaseTypedType
import io.strongtyped.active.slick.Lens._
import org.springframework.data.domain.Pageable
import slick.jdbc.GetResult

import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import scala.language.higherKinds
import com.wix.accord.Validator
import com.wix.accord.dsl._
import reactor.core.publisher.{Flux, Mono}

case class User(createDate:Date,modifyDate:Date,roleNames:String,userName:String,accountId:String,id:Option[Int]=None)

class Users(tag: Tag) extends Table[User](tag,"t_users"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def createDate = column[Date]("createDate",O.Default(new Date(new java.util.Date().getTime)))
  def modifyDate = column[Date]("modifyDate",O.Default(new Date(new java.util.Date().getTime)))
  def roleNames = column[String]("roleNames")
  def userName = column[String]("userName")
  def accountId = column[String]("account_id")
  def * = (createDate,modifyDate,roleNames,userName,accountId,id.?)<>(User.tupled, User.unapply)
}

object Users extends TableQuery(new Users(_)){
  val findByUserName = this.findBy(_.userName)
}

class UserRepo extends EntityActions with MySQLProfileProvider{
  import jdbcProfile.api._
  val baseTypedType = implicitly[BaseTypedType[Id]]
  type Entity = User
  type Id = Int
  type EntityTable = Users
  val tableQuery = Users
  def $id(table:Users):Rep[Id] = table.id
  val idLens = lens { user: User => user.id  }
                    { (user, id) => user.copy(id = id) }
}

object UserRepo extends UserRepo{
  import scala.compat.java8.FutureConverters._
  implicit val getUserResult = GetResult(r => User(r.nextDate(), r.nextDate(), r.nextString(), r.nextString(), r.nextString(), r.nextIntOption()))
  implicit val userFormat = Json.format[User]
  lazy val dbUtil = DatabaseUtil
  implicit val userValidator:Validator[User] = validator[User]{user =>
    user.userName is notEmpty
    user.accountId is notBlank
  }

  def findEntityById(id:Id):Mono[Option[User]] ={
    Mono.fromFuture(dbUtil.getFuture(findOptionById(id)).toJava.toCompletableFuture)
  }

  def countEntity():Mono[Int] = {
    Mono.fromFuture(dbUtil.getFuture(Users.size.result).toJava.toCompletableFuture)
  }

  def saveEntity(entity:Entity):Unit = {
    Mono.fromFuture(dbUtil.getFuture(save(entity)).toJava.toCompletableFuture)
  }

  def findByUserNameAndId(userName:String,id:Int):Flux[User] = {
    val action = for {
        user <- tableQuery if user.userName === userName if user.id === id
    }yield user
    Flux.from(dbUtil.getStream(action.result))
  }

  def findByUserNameLike(userName:String):Flux[User] = {
    val action = tableQuery.filter(_.userName like userName).result
    Flux.from(dbUtil.getStream(action))
  }

  def deleteByUserNameLikeWithExec(userName:String):Mono[Int] = {
    val action = tableQuery.filter(_.userName like userName).delete
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findIdByUserNameWithExec(userName:String):Flux[Int] = {
    val action = tableQuery.filter(_.userName === userName).map(_.id).result
    Flux.from(dbUtil.getStream(action))
  }

  def findAllByPageableWithExec(offset:Int,rows : Int):Flux[User] = {
    val action = tableQuery.drop(offset).take(rows).sortBy(_.id.desc).result
    println(action.statements.head)
    Flux.from(dbUtil.getStream(action))
  }

  def findAllByPageableWithExec(page:Pageable):Flux[User] ={
    val sort  = page.getSort.iterator().next()
    val action = tableQuery.drop(page.getOffset).take(page.getPageSize)
    if(sort.isAscending){
      action.sortBy(_.column[String](sort.getProperty).asc.nullsLast)
    }else{
      action.sortBy(_.column[String](sort.getProperty).desc.nullsLast)
    }
    Flux.from(dbUtil.getStream(action.result))
  }

  def findMaxIdWithExec:Flux[Int]={
    val action = tableQuery.map(_.id)
    val maxAction = action.max.result
    println(maxAction.statements.mkString)
    Flux.from(dbUtil.getStream(action.result))
  }

  def getByUserNameWithSqlExec(userName:String):Flux[User] = {
    val action = sql"select * from t_users where userName = $userName".as[User]
    Flux.from(dbUtil.getStream(action))
  }

  def createTableWithExec={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def batchInsertUserWithExec(users:Seq[User])={
    val action = tableQuery ++= users
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def insertUserWithExec(user:User):Mono[Int] = {
    val action = tableQuery returning tableQuery.map(_.id) += user
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def fetchAllWithExec():Flux[User]={
    Flux.from(dbUtil.getStream(this.fetchAll()))
  }

  def createAndBatchInsertUserWithExec(users : Seq[User])={
    val actions =(
      tableQuery.schema.create >>
        (tableQuery ++= users)
      ).transactionally
    Mono.fromFuture(dbUtil.getFuture(actions.asTry).toJava.toCompletableFuture)
  }

  def findByUserNameOrIdWithExec(userName:String,id :Int):Flux[User] = {
    val action = tableQuery.filter{user =>
      List(user.userName === userName,user.id === id).reduceLeftOption(_ || _).getOrElse(true: Rep[Boolean])
    }
    Flux.from(dbUtil.getStream(action.result))
  }

  def updateUserNameByIdWithExec(id:Int,userName:String) = {
    val action = tableQuery.filter(_.id === id).map(_.userName)
    val f = action.update("admin")
    Mono.fromFuture(dbUtil.getFuture(f).toJava.toCompletableFuture)
  }

  def findFirstByUserNameWithExec(userName:String):Mono[Option[User]] = {
    val action = tableQuery.filter(_.userName === userName).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

}
