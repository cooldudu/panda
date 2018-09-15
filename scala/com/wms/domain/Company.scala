package com.wms.domain

import com.wix.accord.Validator
import com.wix.accord.dsl._
import com.wms.core.database.DatabaseUtil
import com.wms.core.utils.common.ObjectUtils
import org.springframework.data.domain.Pageable
import reactor.core.publisher.{Flux, Mono}
import slick.dbio.{DBIOAction, Effect}
import slick.jdbc.GetResult
import slick.jdbc.MySQLProfile.api._
import slick.sql.{FixedSqlAction, SqlAction}

import scala.language.{higherKinds, postfixOps}

case class Company(name:String,descript:String,logo:Option[String],id:Option[Int])

class Companies(tag: Tag)extends Table[Company](tag,"t_companies"){
  def id = column[Int]("id",O.PrimaryKey,O.AutoInc)
  def name = column[String]("name",O.SqlType("varchar(200)"))
  def descript = column[String]("descript")
  def logo = column[String]("logo",O.SqlType("varchar(255)"))
  def * = (name,descript,logo.?,id.?)<>(Company.tupled,Company.unapply)
}

object Companies extends TableQuery(new Companies(_)){}

class CompanyRepo{
  val tableQuery = Companies
  import play.api.libs.json._
  import scala.compat.java8.FutureConverters._

  implicit val getCompanyResult = GetResult(r => Company(r.nextString,r.nextString,r.nextStringOption,r.nextIntOption))

  implicit val companyFormat = Json.format[Company]
  lazy val dbUtil = new DatabaseUtil()

  implicit val companyValidator:Validator[Company] = validator[Company]{company =>
    company.name is notEmpty
  }

  def createTable={
    val action = tableQuery.schema.create
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

  def findTotalNum(name:String):Mono[Int] ={
    val action = tableQuery.filter(company => {
      if(ObjectUtils.isNotEmpty(name)){
        company.name like "%"+name+"%"
      }else{
        LiteralColumn(true)
      }
    })
    Mono.fromFuture(dbUtil.getFuture(action.length.result).toJava.toCompletableFuture)
  }

  def findAllByPageable(name:String,page:Pageable):Flux[Company] ={
    val action = tableQuery.filter(company => {
      if(ObjectUtils.isNotEmpty(name)){
        company.name like "%"+name+"%"
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

  def save(company:String):Mono[Int] = {
    val companyObj = Json.parse(company).validate[Company].fold(
      errors => errors.mkString,
      category => category
    )
    if(companyObj.isInstanceOf[Company])
    {
      val company = companyObj.asInstanceOf[Company]
      if(company.id.getOrElse(0) >0){
        val action = tableQuery.filter(_.id === company.id).update(company)
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }else{
        val action = tableQuery += company
        Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
      }
    }else{
      Mono.just(0)
    }
  }

  def delete(id:Int):Mono[Int] ={
    val action = tableQuery.filter(_.id===id).delete
    Mono.fromFuture(dbUtil.getFuture(action).toJava.toCompletableFuture)
  }

}