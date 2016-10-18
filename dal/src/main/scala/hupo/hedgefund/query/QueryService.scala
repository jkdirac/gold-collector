package hupo.hedgefund.query

import ch.qos.logback.classic.db.names.TableName
import play.api.libs.json._

trait QueryParam

case class SimpleQueryParam(fields: Seq[FieldFilter[_]], offset: Int = 10, limit: Int = 1, orderCombine: List[Order]) extends QueryParam
case class QueryResult[T](totalNumber: Int, offset: Int, limit: Int, result: T)

trait FieldFilter[T] {
  def tableName: Option[String]
  def field: String
  def value: T

  private val reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|" +
    "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

  private val sqlPattern = reg.r

  private def keyWordFilter(value: String): Unit = {
    if (!sqlPattern.findAllMatchIn(value).isEmpty) {
      throw new IllegalArgumentException(s"Sql injection detected! input string: $value")
    }
  }
  protected def handleValue(columnName: String): String;

  def makeFilterSql(columnName: String): String = {
      // security check
    keyWordFilter(value.toString)
    handleValue(columnName)
  }
}

case class EmptyFilter[T](field: String, value: T, tableName: Option[String] = None) extends FieldFilter[T] {
  override def handleValue(columnName: String): String = {
    ""
  }
}

case class EqualFilter[T](field: String, value: T, tableName: Option[String] = None) extends FieldFilter[T] {
  override def handleValue(columnName: String): String = {
    s" $columnName = '$value' "
  }
}

case class NotEqualFilter[T](field: String, value: T, tableName: Option[String] = None) extends FieldFilter[T] {
  override def handleValue(columnName: String): String = {
    s" $columnName != '$value' "
  }
}

case class NullFilter(field: String, value: Option[Boolean], tableName: Option[String] = None) extends FieldFilter[Option[Boolean]] {
  override def handleValue(columnName: String): String = {
    value match {
      case None  => s"$columnName IS NULL"
      case Some(value) => s"$columnName IS NOT NULL"
    }
  }
}


case class LessThanFilter[T](field: String, value: T, tableName: Option[String] = None) extends FieldFilter[T] {
  override def handleValue(columnName: String): String = {
    s" $columnName < '$value' "
  }
}

case class GreaterThanFilter[T](field: String, value: T, tableName: Option[String] = None) extends FieldFilter[T] {
  override def handleValue(columnName: String): String = {
    s" $columnName > '$value' "
  }
}

case class NotInFilter[T](field: String, value: Seq[T], tableName: Option[String] = None) extends FieldFilter[Seq[T]] {
  override def handleValue(columnName: String): String = {
    if (value.isEmpty) {
      return ""
    }

    val valueStrSeq = value.map(row => s"'$row'")
    s" $columnName NOT IN (${valueStrSeq.mkString(",")}) "
  }
}

case class InFilter[T](field: String, value: Seq[T], tableName: Option[String] = None) extends FieldFilter[Seq[T]] {
  override def handleValue(columnName: String): String = {
    if (value.isEmpty) {
      return ""
    }

    val valueStrSeq = value.map(row => s"'$row'")
    s" $columnName IN (${valueStrSeq.mkString(",")}) "
  }
}

case class NotContainsFilter[T](field: String, value: Seq[T], tableName: Option[String] = None) extends FieldFilter[Seq[T]] {
  override def handleValue(columnName: String): String = {
    val valueStrSeq = value.map(v =>
      s"$columnName NOT LIKE '%$v%' "
    )

    valueStrSeq.mkString(" AND ")
  }
}

case class ContainsFilter[T](field: String, value: Seq[T], tableName: Option[String] = None) extends FieldFilter[Seq[T]] {
  override def handleValue(columnName: String): String = {
    val valueStrSeq = value.map(v =>
      s"$columnName LIKE '%$v%' "
    )

    val orSeq = valueStrSeq.mkString(" OR ")
    if (orSeq.trim.isEmpty) {
      ""
    } else {
      s"($orSeq)"
    }
  }
}


case class ValueRangeFilter[T](field: String, value: Seq[(T, T)], tableName: Option[String] = None) extends FieldFilter[Seq[(T, T)]] {
  override def handleValue(columnName: String): String = {
    if (value.isEmpty) {
      return ""
    }

    val valueStrSeq = value.map(pair => s" ${columnName} > '${pair._1.toString}' AND ${columnName} <= '${pair._2.toString}'")
    s" (${valueStrSeq.mkString(" OR ")}) "
  }
}

object ValueRangeFilter{
  def apply(field: String, jsValue: JsValue, dataType: Option[String]): ValueRangeFilter[Any] = {
    val ranges = jsValue.as[JsArray].value map { case subArray =>
      val subArrayValues = subArray.as[JsArray].value

      (subArrayValues(0), subArrayValues(1))
    }

    dataType match {
      case None => ValueRangeFilter(field, ranges)
      case Some("time")=> ValueRangeFilter(field,
        ranges.map {
          case (JsString(str1), JsString(str2))=> (str1, str2)
          case _ => throw new IllegalArgumentException("Need string here")
        })
      case _ => throw new IllegalArgumentException("Invalid query string")
    }
  }
}

object FieldFilterOperation extends Enumeration {
  type FieldFilterOperation = Value

  val EQ = Value("$eq")
  val NEQ = Value("$neq")
  val LESS_THAN = Value("$ls")
  val GREATER_THAN = Value("$gt")
  val VALUE_RANGES = Value("$ranges")
  val CONTAINS = Value("$contains")
  val IN = Value("$in")
  val NOT_IN = Value("$nin")
  val EXISTS = Value("$exists")
}

case class Order(field: String, isDesc: Boolean, tableName: Option[String] = None) {
  def generate(columnName: String): String = {
    if (isDesc) s"$columnName DESC" else columnName
  }
}

object SimpleQueryParam {
  private def parseOperatedObj(jsValue: JsValue): Seq[String] = {
    jsValue match {
      case array: JsArray => array.value.map(value => value.as[JsString].value)
      case jsString: JsString => Seq(jsString.value)
      case others  => throw new IllegalArgumentException(s"Invalid format $others")
    }
  }
  def apply(): SimpleQueryParam = {
    new SimpleQueryParam(fields = Seq(), limit = Int.MaxValue, offset = 0, orderCombine = List())
  }

  def apply(jsBody: JsValue, offset: Int, limit: Int, sortInput: String): SimpleQueryParam = {
    val root = jsBody.as[JsObject].value
    val fields = root.map {
      case (key, value) =>
      val filter = value match {
        case JsString(value) => EqualFilter(key, value)
        case JsObject(obj) => {
          if (obj.isEmpty) {
            EmptyFilter("", "")
          } else {
            val operationValue = obj.head
            val operator = FieldFilterOperation.withName(operationValue._1)
            val operatedObj = operationValue._2
            operator match {
              case FieldFilterOperation.NOT_IN => NotInFilter(key, parseOperatedObj(operatedObj))
              case FieldFilterOperation.IN => InFilter(key, parseOperatedObj(operatedObj))
              case FieldFilterOperation.CONTAINS => ContainsFilter(key, parseOperatedObj(operatedObj))
              case FieldFilterOperation.VALUE_RANGES => ValueRangeFilter(key, operatedObj, obj.get("__type").map(_.as[JsString].value))
            }
          }
        }
        case _ => throw new IllegalArgumentException(s"Invalid input ${key} ${value}")
      }
      filter
    }

    val orderCombine = if (sortInput.isEmpty) {
      List()
    }
    else {
      sortInput.split(",").toList.map(orderStr =>
        if (orderStr.startsWith("-")) {
          new Order(orderStr.substring(1, orderStr.length), true)
        } else {
          new Order(orderStr, false)
        }
      )
    }

    val orderNotEmpty = orderCombine.map(order => NullFilter(order.field, Some(true)))
    new SimpleQueryParam(fields = fields.toSeq ++ orderNotEmpty, limit = limit, offset = offset, orderCombine = orderCombine)
  }
}
