package hupo.hedgefund.common

import java.sql.BatchUpdateException
import java.time.ZonedDateTime

import hupo.hedgefund.postgres.ugc.ClientUserRow
import hupo.postgres.driver.PostgresDriver.api._
import hupo.postgres.driver.ServicePostgresDSI
import org.h2.jdbc.JdbcSQLException
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object PgDAOCommon {
  protected val log = LoggerFactory.getLogger(classOf[PgDAOCommon[_]])
}


abstract class PgDAOCommon[T <: Entity] extends Logger {
  import PgDAOCommon.log

  abstract class EntityTable(tag: Tag) extends Table[T](tag, tableName) {
    def uid = column[String]("uid", O.PrimaryKey, O.Length(64, varying = true))

    def createdAt = column[ZonedDateTime]("created_at")

    def updatedAt = column[ZonedDateTime]("updated_at")

    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))

    def createdAtIdx = index(indexName("created_at"), createdAt)

    def updatedAtIdx = index(indexName("updated_at"), updatedAt)
  }

  type TableType <: EntityTable

  type QueryType <: TableQuery[TableType]

  def tableName: String
  def tableMeta: TableMeta = TableMeta(dsi.db, tableName)

  protected def indexName(column: String, postfix: String = "idx") = s"${tableName}_${column}_$postfix"

  protected def foreignKeyName(column: String, postfix: String = "fk") = s"${tableName}_${column}_$postfix"

  def dsi: ServicePostgresDSI

  def table: QueryType

  def schema = table.schema

  def exists: Boolean = {
    val query = sql"""SELECT EXISTS (
      SELECT 1
      FROM   information_schema.tables
      WHERE  table_name = '#$tableName'
      )""".as[Boolean]
    Await.result(dsi.db.run(query), Duration.Inf).head
  }

  //we explicitly want create/drop schema to be synchronous here
  //because it's supposed to be execute once and once only
  def createSchema: Unit = if (!exists) {
    Await.result(dsi.db.run(DBIO.seq(schema.create)), Duration.Inf)
  }


  def dropSchema: Unit = if (exists) {
    Await.result(dsi.db.run(DBIO.seq(schema.drop)), Duration.Inf)
  }

  def selectAll(): Future[Seq[T]] = {
    val query = table.result
    dsi.db.run(query)
  }

  def get(uid: String): Future[T] = {
    getOption(uid).map(_.getOrElse {
      throw new IllegalArgumentException(s"$uid not found from $tableName")
    })
  }

  def getOption(uid: String): Future[Option[T]] = {
    val query = table.filter(_.uid === uid).filter(_.isDeleted === false).result
    dsi.db.run(query).map { case res => res.headOption }
  }

  def batchGet(uidSeq: Seq[String]): Future[Seq[T]] = {
    if (uidSeq.isEmpty) {
      Future successful Seq()
    }

    val query = table.filter(_.uid inSet uidSeq).filter(_.isDeleted === false).result
    dsi.db.run(query)
  }

  def insert(entity: T): Future[Unit] = dsi.db.run(DBIO.seq(table += entity))
  def update(entity: T): Future[Int] = {
    val update = table.filter(_.uid === entity.uid).map(v => v).update(entity)
    dsi.db.run(update)
  }

  private def insertAll(entities: Seq[T]): Future[Unit] = dsi.db.run(DBIO.seq(table ++= entities))
  def batchInsert(entities: Seq[T], batchSize: Int = 50): Future[Seq[T]] = {
    val resultFut = insertAll(entities = entities).map(_ => entities)

    if (entities.size > batchSize) {
    }

    resultFut onFailure {
      case e: BatchUpdateException => {
        log.error("Batch insert failed ", e)
        val nextException = e.getNextException
        if (nextException != null) {
          log.error("next exception ", nextException)
        }
      }
      case t: Throwable => log.error("", t)
    }

    resultFut
  }

  def upsert(entity: T): Future[T] = dsi.db.run(table.insertOrUpdate(entity)).map(_ => entity)
  def upsert(entities: Seq[T]): Future[Seq[T]] = {
    val ops = entities.map(e => table.insertOrUpdate(e))
    val action = DBIO.seq(ops: _*)
    dsi.db.run(action).map(_ => entities)
  }

  def delete(uid: String, operator: String): Future[Int] = {
    val deleteAction = table.filter(_.uid === uid).delete
    dsi.db.run(deleteAction)
  }

  def delete(uids: Seq[String], operator: String): Future[Int] = {
    val deleteAction = table.filter(_.uid inSet uids.toSet).delete
    dsi.db.run(deleteAction)
  }

  def mergeOptionValue[V](newValue: Option[V], defaultValue: Option[V]): Option[V] = {
    if (newValue.isDefined) {
      newValue
    } else {
      defaultValue
    }
  }

  def getModifiedKeyValue(newValue: Product): Map[String, Any] = {
    val fields = newValue.getClass.getDeclaredFields.map(_.getName)

    val noNoneValues = fields.zip(newValue.productIterator.to).toMap.filter {
      case (key, None) => false
      case _ => true
    }

    noNoneValues map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
  }
}