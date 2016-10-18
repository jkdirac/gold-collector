package hupo.hedgefund.common

import java.time.ZonedDateTime

import hupo.postgres.driver.ServicePostgresDSI
import slick.lifted.Tag
import hupo.postgres.driver.PostgresDriver.api._
import play.api.libs.json.{JsValue, Json}

case class EventSourcingRow(uid: String, operatedUid: String, operatedTableName: String, operation: String, snapshot: Option[JsValue] = None,
  isDeleted: Boolean = false, createdAt: ZonedDateTime = ZonedDateTime.now(), updatedAt: ZonedDateTime = ZonedDateTime.now()) extends Entity {
  implicit val format = Json.format[EventSourcingRow]

  override def toJson(): JsValue = Json.toJson(this)
}

class EventSourcingDAO(val dsi: ServicePostgresDSI) extends PgDAOCommon[EventSourcingRow] {
  type TableType = EntityPgTable
  type QueryType = TableQuery[EntityPgTable]

  val tableName = "event_sourcing"

  class EntityPgTable(tag: Tag) extends EntityTable(tag) {
    def * = (uid, operatedUid, operatedTableName, operation, snapshot, isDeleted, createdAt, updatedAt) <> (EventSourcingRow.tupled, EventSourcingRow.unapply)

    /** Database column operated_uid SqlType(varchar), Length(48,true) */
    val operatedUid: Rep[String] = column[String]("operated_uid", O.Length(48,varying=true))
    /** Database column operated_table_name SqlType(varchar), Length(128,true) */
    val operatedTableName: Rep[String] = column[String]("operated_table_name", O.Length(128,varying=true))
    /** Database column operation SqlType(varchar), Length(8,true) */
    val operation: Rep[String] = column[String]("operation", O.Length(8,varying=true))
    /** Database column snapshot SqlType(jsonb), Length(2147483647,false), Default(None) */
    val snapshot: Rep[Option[JsValue]] = column[Option[JsValue]]("snapshot")

    /** Index over (createdAt) (database name event_sourcing_created_at_idx) */
    val index1 = index("event_sourcing_created_at_idx", createdAt)
    /** Index over (operatedUid) (database name event_sourcing_operated_id_at_idx) */
    val index2 = index("event_sourcing_operated_id_at_idx", operatedUid)
    /** Index over (operatedTableName) (database name event_sourcing_operated_table_name_at_idx) */
    val index3 = index("event_sourcing_operated_table_name_at_idx", operatedTableName)
    /** Index over (updatedAt) (database name event_sourcing_updated_at_idx) */
    val index4 = index("event_sourcing_updated_at_idx", updatedAt)
  }

  val table = TableQuery[EntityPgTable]
}
