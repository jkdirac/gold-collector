package hupo.hedgefund.query

import hupo.hedgefund.common.TableMeta

/**
  * Use Joiner
  */
@Deprecated
class TableJoiner(filters: Seq[FieldFilter[_]], orderCombine: List[Order], tableSeq: Seq[(String, TableMeta)], columnMapper: Map[String, String] = Map()) {
  val filterSeq = tableSeq map { case (tableName, tableMeta) => getWhereClauseByTable(filters, tableName, tableMeta) }
  val foldedFilterSeq = filterSeq.foldLeft(Seq[String]())((s, result) => if (s.isEmpty) result else s ++ result )

  val sortSeq = tableSeq map { case (tableName, tableMeta) => getSortByTable(orderCombine, tableName, tableMeta) }
  val foldSortSeq = sortSeq.foldLeft(Seq[(Int, String)]())((a, result) =>
    if (a.isEmpty) result else result ++ a
  ).sortBy(_._1).map(_._2)

  private def mappedColumnName(column: String): String = {
    columnMapper.get(column).getOrElse(column)
  }

  def getWhereClause(): String = { s"${foldedFilterSeq.mkString(" AND ")}".trim }

  def getGroupByClause(): String = {
    val filterSeq = tableSeq flatMap { case (tableName, tableMeta) =>
      filters.flatMap(filter => {
        val columnMetaOption = tableMeta.getColumnMetaOption(mappedColumnName(filter.field))

        columnMetaOption.map(meta =>
          s"$tableName.${meta.name}"
        )
      })
    }

    val sortSeq = tableSeq flatMap { case (tableName, tableMeta) =>
      orderCombine.zipWithIndex.flatMap { case (order, index) =>
        val columnMetaOption = tableMeta.getColumnMetaOption(mappedColumnName(order.field))

        columnMetaOption map { case meta =>
          s"$tableName.${meta.name}"
        }
      }
    }

    (sortSeq ++ filterSeq).mkString(",").trim
  }

  def getSortClause(): String = {
    if (foldSortSeq.isEmpty)
      s""
    else
      s"""ORDER BY ${foldSortSeq.mkString(",")}"""
  }

  private def getWhereClauseByTable(filters: Seq[FieldFilter[_]], tableName: String, tableMeta: TableMeta): Seq[String] = {
    filters.flatMap(filter => {
      val columnMetaOption = tableMeta.getColumnMetaOption(mappedColumnName(filter.field))

      columnMetaOption.map(meta =>
        filter.makeFilterSql(s"$tableName.${meta.name}")
      )
    })
  }

  private def getSortByTable(orderCombine: List[Order], tableName: String, tableMeta: TableMeta): Seq[(Int, String)] = {
    orderCombine.zipWithIndex.flatMap { case (order, index) =>
      val columnMetaOption = tableMeta.getColumnMetaOption(mappedColumnName(order.field))

      columnMetaOption map { case meta =>
        val direction = if (order.isDesc) "DESC" else ""
        (index, s"$tableName.${meta.name} $direction")
      }
    }
  }
}


