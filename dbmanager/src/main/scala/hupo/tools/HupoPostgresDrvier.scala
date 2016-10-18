package hupo.tools

/**
  * Created by kunjiang on 16/9/5.
  */

import scala.reflect.{classTag}
import com.github.tminglei.slickpg._
import play.api.libs.json.{JsValue, Json}
import slick.driver.JdbcProfile
import slick.profile.Capability

trait PostgresDriver extends ExPostgresDriver
  with PgArraySupport
  with PgPlayJsonSupport
  with PgNetSupport
  with PgLTreeSupport
  with PgRangeSupport
  with PgHStoreSupport
  with PgEnumSupport
  with PgSearchSupport
  with PgDate2Support {

  override val pgjson = "jsonb"
  ///
  override val api = PgAPI

  object PgAPI extends API with ArrayImplicits
    with PlayJsonImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants
    with DateTimeImplicits {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val playJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => utils.SimpleArrayUtils.fromString[JsValue](Json.parse(_))(s).orNull,
        (v) => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)
  }

  // Add back `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  override protected def computeCapabilities: Set[Capability] =
    super.computeCapabilities + JdbcProfile.capabilities.insertOrUpdate

  {
    bindPgTypeToScala("text[]", classTag[List[String]])
    bindPgTypeToScala("jsonb", classTag[JsValue])
    bindPgTypeToScala("hstore", classTag[Map[String, String]])
  }
}

object PostgresDriver extends PostgresDriver
