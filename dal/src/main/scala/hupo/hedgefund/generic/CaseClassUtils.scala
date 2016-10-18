package hupo.hedgefund.generic

import java.sql.{Date, Timestamp}

import common.Logger
import hupo.hedgefund.common.DateTimeUtils
import play.api.libs.json._

/**
  * Created by kunjiang on 16/9/10.
  */
object CaseClassUtils extends Logger {

  def isEmpty[T <: Product](product: T): Boolean = {
    product.productIterator.foldLeft(true) {
      case (result, None) => result && true
      case _ => false
    }
  }

  def toMap(caseObj: Product): Map[String, Any] = {
    val fields = caseObj.getClass.getDeclaredFields.map(_.getName)

    val noNoneValues = fields.zip(caseObj.productIterator.to).toMap.filter {
      case (key, None) => false
      case _ => true
    }

    noNoneValues map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
  }

  def convertMap2JsObject(mapValue: Map[String, Any]): JsValue = {
    try {
      JsObject(mapValue.map {
        case ("id", v: Int) =>  ("id", JsString(v.toString))
        case (key, v: Int) =>  (key, JsNumber(v))
        case (key, v: Double) => (key, JsNumber(v))
        case (key, v: BigDecimal) => (key, JsNumber(v))
        case (key, v: String) => (key, JsString(v))
        case (key, v: Date) => (key, JsString(DateTimeUtils.sqlDateString(v)))
        case (key, v: Timestamp) => (key, JsString(DateTimeUtils.timeStampString(v)))
        case (key, None) => (key, JsNull)
        case (key, value) => throw new IllegalArgumentException(s"Don't support query ${value.toString}:${value.getClass} type")
      })
    } catch {
      case e: Throwable => {
        logger.error(s"convert failed ${mapValue}", e)
        throw e
      }
    }
  }
}

// 支持输出Null
object NullableWrites extends DefaultWrites{

  override def OptionWrites[T](implicit fmt: Writes[T]): Writes[Option[T]] = new Writes[Option[T]] {
    override def writes(o: Option[T]): JsValue = {
      o match {
        case Some(a) => Json.toJson(a)(fmt)
        case None => JsNull
      }
    }
  }
}
