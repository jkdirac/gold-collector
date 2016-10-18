package hupo.hedgefund.generic

import java.sql.{Date, Timestamp}

import hupo.hedgefund.common.DateTimeUtils
import play.api.libs.json._

/**
  * Created by kunjiang on 16/9/10.
  */
object OptionUtils {

  def optionDivide(nominatorOpt: Option[BigDecimal], denominatorOpt: Option[BigDecimal]): Option[BigDecimal] = {
    (nominatorOpt, denominatorOpt) match {
      case (Some(nominator), Some(denominator)) => Some(nominator / denominator)
      case _ => None
    }
  }
}

