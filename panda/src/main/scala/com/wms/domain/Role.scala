package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import com.wms.core.utils.common.ObjectUtils
import org.springframework.data.domain.Pageable
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class Role(roleName: String, showName: String, id: Option[Int] = None)

class Roles(tag: Tag) extends Table[Role](tag, "t_roles") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def roleName = column[String]("roleName",O.Unique)

  def showName = column[String]("showName")

  def * = (roleName, showName, id.?) <> (Role.tupled, Role.unapply)
}

object Roles extends TableQuery(new Roles(_)) {}

class RoleRepo{
  val tableQuery = Roles
  import scala.compat.java8.FutureConverters._
  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._

  implicit val getRoleResult = GetResult(r => Role(r.nextString(), r.nextString(), r.nextIntOption()))

  implicit val roleFormat = Json.format[Role]
  lazy val dbUtil = new DatabaseUtil()
  implicit val roleValidator: Validator[Role] = validator[Role] { role =>
    role.roleName is notEmpty
  }

  def findTotalNum(roleName: String, roleType: String): Mono[Int] = {
    val action = tableQuery.filter(role => {
      if(ObjectUtils.isNotEmpty(roleName)){
        role.showName like "%" + roleName + "%"
      }else{
        LiteralColumn(true)
      }
    }).filter(role => {
      if(ObjectUtils.isNotEmpty(roleType)&&roleType.equals("system")){
        role.roleName === "SYSTEM"
      }else if(ObjectUtils.isNotEmpty(roleType)&&roleType.equals("user")){
        role.roleName =!= "SYSTEM"
      }else{
        LiteralColumn(true)
      }
    })
    Mono.fromFuture(dbUtil.getFuture(action.length.result).toJava.toCompletableFuture)
  }

  def findAllByPageable(roleName: String, roleType: String, page: Pageable): Flux[Role] = {
    val action = tableQuery.filter(role => {
      if(ObjectUtils.isNotEmpty(roleName)){
        role.showName like "%" + roleName + "%"
      }else{
        LiteralColumn(true)
      }
    }).filter(role => {
      if(ObjectUtils.isNotEmpty(roleType)&&roleType.equals("system")){
        role.roleName === "SYSTEM"
      }else if(ObjectUtils.isNotEmpty(roleType)&&roleType.equals("user")){
        role.roleName =!= "SYSTEM"
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
    Flux.from( dbUtil.getStream(action.result))
  }

  def save(role:String):Mono[Int] ={
    val roleObj = Json.parse(role).validate[Role].fold(
      errors => errors.mkString,
      category => category
    )
    if(roleObj.isInstanceOf[Role])
    {
      val role  = roleObj.asInstanceOf[Role]
      if(role.id.getOrElse(0) > 0){
        val action = tableQuery.filter(_.id===role.id).update(role)
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }else {
        val action = tableQuery += role
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
}