package com.wms.core.domain

import com.wms.core.database.DatabaseUtil
import slick.codegen.SourceCodeGenerator
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
class GeneratorUtil {

}

object GeneratorUtil extends App{
//    slick.codegen.SourceCodeGenerator.main(
//      Array("slick.jdbc.MySQLProfile","com.mysql.jdbc.Driver",
//      "jdbc:mysql://localhost:3306/pandanew?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false",
//      "c:/domain","com.wms.core.domain","root","admin")
//    )
    val modelAction = MySQLProfile.createModel(Some(MySQLProfile.defaultTables))
    val db = new DatabaseUtil().getDatabase()
    val modelFuture = db.run(modelAction)
    // customize code generator
    val codegenFuture = modelFuture.map(model => new SourceCodeGenerator(model) {
      // override mapped table and class name
      override def entityName =
        dbTableName => dbTableName.substring(2).toLowerCase.toCamelCase

      override def tableName =
        dbTableName => dbTableName.toLowerCase.toCamelCase

      // add some custom import
      override def code = "import play.api.libs.json._" + "\n" + super.code

      // override table generator
      override def Table = new Table(_) {
        // disable entity class generation and mapping
        override def EntityType = new EntityType {
          override def classEnabled = false
        }

        // override contained column generator
        override def Column = new Column(_) {
          // use the data model member of this column to change the Scala type,
          // e.g. to a custom enum or anything else
          override def rawType = super.rawType
            //if (model.name == "createDate") "java.sql.Date" else super.rawType
        }
      }
    })
    try {
      Some(Await.result(codegenFuture, Duration.Inf)).get.writeToFile("slick.jdbc.MySQLProfile", "c:/domain", "com.wms.core.domain")
    }finally db.close()
}
