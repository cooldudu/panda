package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class Account(isAccoumtNonExpired:Boolean,isAccountNonLocked:Boolean,
                   isCredentialsNonExpired:Boolean,enabled:Boolean,userName:String,password:String,id:Option[Int]=None)

class Accounts(tag:Tag) extends Table[Account](tag,"t_accounts"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def isAccoumtNonExpired = column[Boolean]("isAccoumtNonExpired",O.Default(true))
  def isAccountNonLocked = column[Boolean]("isAccountNonLocked",O.Default(true))
  def isCredentialsNonExpired = column[Boolean]("isCredentialsNonExpired",O.Default(true))
  def enabled = column[Boolean]("enabled",O.Default(true))
  def userName = column[String]("userName",O.Unique)
  def password = column[String]("password")
  def * = (isAccoumtNonExpired,isAccountNonLocked,
    isCredentialsNonExpired,enabled,userName,password,id.?)<>(Account.tupled,Account.unapply)
}

object Accounts extends TableQuery(new Accounts(_)){}

class AccountRepo{
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._
  val tableQuery = Accounts
  implicit val getAccountResult = GetResult(r => Account(r.nextBoolean(),r.nextBoolean(),
    r.nextBoolean(),r.nextBoolean(),r.nextString(),r.nextString(),r.nextIntOption()))

  implicit val accountFormat = Json.format[Account]
  lazy val dbUtil = new DatabaseUtil()
  implicit  val accountValidator:Validator[Account] = validator[Account]{account =>
    account.userName is notEmpty
  }

  def findByUserName(userName:String) = {
    val action = tableQuery.filter(_.userName === userName).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

}
