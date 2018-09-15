package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import com.wms.core.utils.common.ObjectUtils
import com.wms.core.utils.operator.OperatorUtil
import reactor.core.publisher.{Flux, Mono}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._

import scala.language.{higherKinds, postfixOps}

case class PageInvocation(entityName: String, entityType: String, urls: String, menuIds: String, buttonIds: String, id: Option[Int])

class PageInvocations(tag: Tag) extends Table[PageInvocation](tag, "t_pageinvocations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def entityName = column[String]("entityName", O.SqlType("varchar(255)"))

  def entityType = column[String]("entityType", O.SqlType("varchar(20)"))

  def urls = column[String]("urls", O.SqlType("text"))

  def menuIds = column[String]("menuIds")

  def buttonIds = column[String]("buttonIds")

  def * = (entityName, entityType, urls, menuIds, buttonIds, id.?) <> (PageInvocation.tupled, PageInvocation.unapply)
}

object PageInvocations extends TableQuery(new PageInvocations(_)) {}

class PageInvocationRepo {
  val tableQuery = PageInvocations
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._

  implicit val getRoleResult = GetResult(r => PageInvocation(r.nextString(), r.nextString(), r.nextString(), r.nextString(), r.nextString(), r.nextIntOption()))

  implicit val pageInvocationFormat = Json.format[PageInvocation]
  lazy val dbUtil = new DatabaseUtil()
  implicit val pageInvocationValidator: Validator[PageInvocation] = validator[PageInvocation] { pageInvocation =>
    pageInvocation.entityType is notEmpty
  }

  def createTable = {
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findByEntityTypeAndName(entityType: String, entityName: String): Mono[Option[PageInvocation]] = {
    val action = tableQuery.filter(_.entityName === entityName).filter(_.entityType === entityType).result.headOption
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def createPageInvocation(entityType: String, entityName: String, obj: JsValue) = {
    val result = parseJson(obj)
    val pageInvocation = new PageInvocation(entityName, entityType, result._1, result._2, result._3, None)
    val action = tableQuery += pageInvocation
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def editUrl(pageInvocation: PageInvocation, obj: JsValue) = {
    val result = parseJson(obj)
    val action = tableQuery.filter(_.id === pageInvocation.id).map(pi => (pi.urls, pi.menuIds, pi.buttonIds)).update(result._1, result._2, result._3)
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def parseJson(obj: JsValue): (String, String, String) = {
    val urls = new StringBuilder
    val menuIds = new StringBuilder
    val buttonIds = new StringBuilder
    buttonIds.append("[")
    obj.as[JsArray].value.map { item =>
      if (item.\("menuUid").isDefined) {
        val menuUid = item.\("menuUid").as[String].trim
        val menuOpt = new MenuRepo().findByUid(menuUid).block()
        if (menuOpt.nonEmpty) {
          urls.append(menuOpt.get.url.get + ";")
          menuIds.append(menuOpt.get.uid + ",")
        }
      }
      if (item.\("checkboxGroup").isDefined) {
        val menuUid = item.\("menuUid").as[String].trim
        val menuOption = new MenuRepo().findByUid(menuUid).block
        if (menuOption.nonEmpty) {
          val menu = menuOption.get
          item.\("checkboxGroup").as[JsArray].value.map { btn =>
            val operator = OperatorUtil.findOperatorDataByMenuNameAndMethodName(menu.name, btn.as[String])
            if (ObjectUtils.isNotEmpty(operator)) {
              urls.append(operator.get("path") + ";")
              buttonIds.append("{\"name\":\"" + operator.get("methodName") + "\",\"menuUid\":\"" + menu.uid + "\"},")
            }
          }
        }
      }
    }
    import collection.JavaConverters._
    for(url <- OperatorUtil.findDefaultUrls.asScala){
      urls.append(url + ";")
    }
    (urls.toString, menuIds.toString, buttonIds.toString.substring(0, buttonIds.length - 1) + "]")
  }
}

