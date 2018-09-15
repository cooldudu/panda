package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import reactor.core.publisher.Flux
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class Authority (accountId:Int,roleId:Int,id:Option[Int]=None)

class Authorities(tag:Tag)extends Table[Authority](tag,"t_authorities"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def accountId = column[Int]("account_id")
  def roleId = column[Int]("role_id")
  def * = (accountId,roleId,id.?)<>(Authority.tupled,Authority.unapply)
  def account = foreignKey("account_fk",accountId,Accounts)(_.id,onDelete=ForeignKeyAction.NoAction)
  def role = foreignKey("role_fk",roleId,Roles)(_.id,onDelete=ForeignKeyAction.NoAction)
}

object Authorities extends TableQuery(new Authorities(_)){}

class AuthorityRepo{
  import play.api.libs.json._
  val tableQuery = Authorities
  implicit val getAutherityResult = GetResult(r => Authority(r.nextInt(),r.nextInt(),r.nextIntOption()))
  implicit val authorityFormat = Json.format[Authority]
  lazy val dbUtil = new DatabaseUtil()
  implicit val authorityValidator:Validator[Authority] = validator[Authority]{authority =>
    authority.accountId must be >0
    authority.roleId must be >0
  }

  def findByAccountId(accountId:Int) = {
    val action = tableQuery.filter(_.accountId === accountId).result
    Flux.from( dbUtil.getStream(action))
  }
}