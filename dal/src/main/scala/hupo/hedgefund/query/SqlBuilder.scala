package hupo.hedgefund.query

import hupo.hedgefund.common.{Logger, TableMeta}

/**
  * Created by kunjiang on 16/9/11.
  */
trait Table {
  def joinClause(): Option[String]
  def whereClause(): Seq[String]
  def orderByClause(): Seq[String]
}

case class SubQuery(queryParam: SimpleQueryParam, columnMapper: Option[Map[String, String]], statement: SqlStatement, tableShortName: String,
  onClause: Option[String] = None, joinOperation: String = "JOIN", optional: Boolean = false) extends Logger with Table {

  override def joinClause(): Option[String] = {
    val query = statement.selectKeySql("select * ")

    if (onClause.isEmpty) {
      Some(s""" FROM ($query) $tableShortName """)
    } else {
      Some(s""" $joinOperation ($query) $tableShortName ON ${onClause.get}""")
    }
  }

  override def whereClause(): Seq[String] = Seq()

  override def orderByClause(): Seq[String] = Seq()
}

case class SimpleTable(queryParam: SimpleQueryParam, columnMapper: Option[Map[String, String]], tableMeta: TableMeta, tableShortName: String,
  onClause: Option[String] = None, joinOperation: String = "JOIN", optional: Boolean = false) extends Logger with Table {
  // 首先用设置的mapper进行一次转换,然后调用数据库tableMeta map到数据库真实的字段,如果返回为None,
  // 表示这个字段不在此表内, 将不会生成对应的sql语句
  val mappedFilters = queryParam.fields.flatMap(filter => {
    val mappedValueOption = keyToColumnName(filter.field, filter.tableName)
    val result = mappedValueOption.map(value => (value, filter))
    result
  })

  val mappedOrders = queryParam.orderCombine.flatMap(order => {
    val mappedValueOption = keyToColumnName(order.field, order.tableName)
    mappedValueOption.map(value => (value, order))
  })

  private def keyToColumnName(field: String, tableNameOption: Option[String] = None): Option[String] = {

    if (tableNameOption.isDefined && !tableNameOption.get.equals(tableMeta.tableName())) { //如果是加tableName限制符的
      None
    } else {
      val mappedColumnName = columnMapper.flatMap(_.get(field)).getOrElse(field)
      tableMeta.getColumnMetaOption(mappedColumnName).map(meta => s"${tableShortName}.${meta.name}")
    }
  }

  override def joinClause(): Option[String] = {
    if (optional && mappedFilters.isEmpty && mappedOrders.isEmpty) {
      None
    } else if (onClause.isEmpty) {
      Some(s" $joinOperation ${tableMeta.tableName()} $tableShortName")
    } else {
      Some(s" $joinOperation ${tableMeta.tableName()} $tableShortName ON ${onClause.get} ")
    }
  }

  override def whereClause(): Seq[String] = {
    val filterSeq = mappedFilters map { case (column, filter) =>
      filter.makeFilterSql(column)
    }

    filterSeq
  }

  override def orderByClause(): Seq[String] = {
    val orders = mappedOrders map { case (column, order) =>
      order.generate(column)
    }

    orders
  }
}

case class SqlBuilder(baseTable: Option[Table] = None,
  tables: Seq[Table], queryParam: SimpleQueryParam,
  columnMapper: Option[Map[String, String]] = None) {

  def base(tableMeta: TableMeta, shortName: String): SqlBuilder = {
    this.copy(baseTable = Some(SimpleTable(queryParam, columnMapper, tableMeta, shortName, None, "FROM")))
  }

  def baseSubQuery(statement: SqlStatement, shortName: String): SqlBuilder = {
    this.copy(baseTable = Some(SubQuery(queryParam, columnMapper, statement, shortName, None, "FROM")))
  }

  def join(tableMeta: TableMeta, tableShortCut: String, onClause: String, optional: Boolean = false): SqlBuilder = {
    this.copy(tables = tables :+ SimpleTable(queryParam, columnMapper, tableMeta, tableShortCut, Some(onClause), "JOIN", optional))
  }

  def leftJoin(tableMeta: TableMeta, tableShortCut: String, onClause: String, optional: Boolean = false): SqlBuilder = {
    this.copy(tables = tables :+ SimpleTable(queryParam, columnMapper, tableMeta, tableShortCut, Some(onClause), "LEFT JOIN", optional))
  }

  def build(): SqlStatement = {
    if (baseTable.isEmpty) {
      throw new IllegalArgumentException("missing base table")
    }
    new SqlStatement(baseTable = baseTable.get, tables = this.tables, queryParam = queryParam, columnMapper = columnMapper)
  }
}

object SqlStatement {
  def apply(queryParam: SimpleQueryParam, columnMapper: Option[Map[String, String]] = None): SqlBuilder =
    new SqlBuilder(queryParam = queryParam, columnMapper = columnMapper, tables = Seq())
}

class SqlStatement(baseTable: Table, tables: Seq[Table], queryParam: SimpleQueryParam, columnMapper: Option[Map[String, String]] = None) extends Logger {

  val pageClause = s" LIMIT ${queryParam.limit} OFFSET ${queryParam.offset} "

  def select(selectClause: String): String = {
    s"""select $selectClause
       |    ${generateJoinClause()}
       |        ${generateWhereClause()}
       |        ${generateOrderByClause()} $pageClause
     """.stripMargin
  }

  def selectKeySql(selectClause: String): String = {
    s"""$selectClause
       |    ${generateJoinClause()}
       |        ${generateWhereClause()}
       |        ${generateOrderByClause()} $pageClause
     """.stripMargin
  }

  def selectCountSql(selectClause: String): String = {
    s"""SELECT count(1)
         FROM (
             $selectClause
                 ${generateJoinClause()}
                     ${generateWhereClause()}
                     ${generateOrderByClause()}) _generated
      """.stripMargin
  }

  private def generateJoinClause(): String = {
    val mappedJoinTables = tables flatMap { case table =>
        table.joinClause()
    }

    val baseTableStrOption = baseTable.joinClause()
    if (baseTableStrOption.isEmpty) throw new IllegalArgumentException("Base table is empty!")
    val baseTableStr = baseTableStrOption.get
    if (mappedJoinTables.isEmpty) {
      baseTableStr
    } else {
      s" $baseTableStr ${mappedJoinTables.mkString(" ")}"
    }
  }

  private def generateWhereClause(): String = {
    val mappedWhereTables = (baseTable +: tables) flatMap { case table =>
        table.whereClause()
    }

    val whereStr = mappedWhereTables.mkString(" AND ")
    if (whereStr.trim.isEmpty) "" else s"WHERE $whereStr"
  }

  private def generateOrderByClause(): String = {
    val mappedOrders = (baseTable +: tables) flatMap { case table  =>
        table.orderByClause()
    }

    val orderByStr = mappedOrders.mkString(",")
    if (orderByStr.trim.isEmpty) "" else s"""ORDER BY $orderByStr"""
  }
}
