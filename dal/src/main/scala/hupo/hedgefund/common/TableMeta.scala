package hupo.hedgefund.common

import hupo.postgres.driver.PostgresDriver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kunjiang on 16/9/2.
  */
case class ColumnMeta(order: Int, name: String, typeName: String)

trait TableMeta {
  def tableName(): String

  def getColumnMeta(keyName: String): ColumnMeta
  def getColumnMetaOption(keyName: String): Option[ColumnMeta];
}

class SimpleTableMeta(db: Database, table: MTable) extends Logger with TableMeta {
  lazy val columns = Await.result(db.run(table.getColumns), Duration.Inf)

  val columnMetaSeq = columns.zipWithIndex map {
    case (columnMeta, index) => (TableMeta.columnToKey(columnMeta.name), ColumnMeta(index, columnMeta.name, columnMeta.typeName))
  }
  val columnMetaMap = columnMetaSeq.toMap

  // 找到大写的字符,换成小写再加上一个下划线
  override val tableName = table.name.name
  override def getColumnMeta(keyName: String): ColumnMeta = {
    val columnMetaOption = getColumnMetaOption(keyName)
    columnMetaOption match {
      case None => throw new IllegalArgumentException(s"${keyName} not existed in table ${table.name.name}")
      case Some(columnMeta) => columnMeta
    }
  }

  override def getColumnMetaOption(keyName: String): Option[ColumnMeta] = {
    val columnOption = columnMetaMap.get(keyName)

    columnOption
  }
}

object TableMeta {
  def apply(db: Database, tableName: String): TableMeta = {
    val tables = Await.result(db.run(MTable.getTables(None, None, None, None)), Duration.Inf).toList
    val mtTable = tables.filter(_.name.name == tableName).head

    return new SimpleTableMeta(db, mtTable)
  }

  def columnToKey(name: String): String = {
    val sb = new StringBuffer()
    var index = 0;
    while (index < name.length) {
      if (name(index) == '_') {
        if (index + 1 < name.length) sb.append(name(index + 1).toUpper)
        index = index + 1;
      } else {
        sb.append(name(index))
      }
      index = index + 1;
    }

    sb.toString
  }
}