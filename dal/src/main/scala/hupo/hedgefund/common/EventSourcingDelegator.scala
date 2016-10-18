package hupo.hedgefund.common

import java.util.UUID

import hupo.hedgefund.postgres.ugc.ClientUserRow
import hupo.postgres.driver.ServiceDSI

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait EventSourcingDelegator[T <: Entity] extends PgDAOCommon[T] {
  val eventSourcingDAO = ServiceDSI.eventSourcingDAO

  override def insert(entity: T): Future[Unit] = {
    val resultFut = super.insert(entity)

    val eventSourcingRow = EventSourcingRow(
      uid = UUID.randomUUID().toString,
      operatedTableName = tableName,
      operatedUid = entity.uid,
      snapshot = Some(entity.toJson()),
      operation = DBOperation.Create.toString
    )

    resultFut.flatMap { result =>
      eventSourcingDAO.insert(eventSourcingRow)
    }
  }

  override def update(entity: T): Future[Int] = {
    val resultFut = super.update(entity)

    val eventSourcingRow = EventSourcingRow(
      uid = UUID.randomUUID().toString,
      operatedTableName = tableName,
      operatedUid = entity.uid,
      snapshot = Some(entity.toJson()),
      operation = DBOperation.Update.toString
    )

    resultFut.flatMap { result =>
      eventSourcingDAO.insert(eventSourcingRow)
    }

    resultFut
  }

  override def upsert(t: T): Future[T] = {
    val resultFut = super.upsert(t)

    val eventSourcingRow = EventSourcingRow(
      uid = UUID.randomUUID().toString,
      operatedTableName = tableName,
      operatedUid = t.uid,
      snapshot = Some(t.toJson()),
      operation = DBOperation.Upsert.toString
    )

    resultFut.flatMap {result =>
      eventSourcingDAO.insert(eventSourcingRow)
    }

    resultFut
  }

  override def upsert(entites: Seq[T]): Future[Seq[T]] = {
    val resultFut = super.upsert(entites)

    val eventSourcingRows = entites.map(t =>
        EventSourcingRow(
          uid = UUID.randomUUID().toString,
          operatedTableName = tableName,
          operatedUid = t.uid,
          snapshot = Some(t.toJson()),
          operation = DBOperation.Upsert.toString
      )
    )

    resultFut.flatMap {result =>
      eventSourcingDAO.batchInsert(eventSourcingRows)
    }
    resultFut
  }

  override def delete(uid: String, operator: String): Future[Int] = {
    val result = super.delete(uid, operator)

    result.map { successNum =>
      if (successNum == 1) {
        val eventSourcingRow = EventSourcingRow(
          uid = UUID.randomUUID().toString,
          operatedTableName = tableName,
          operatedUid = uid,
          operation = DBOperation.Delete.toString
        )
        eventSourcingDAO.insert(eventSourcingRow)
      }
    }

    result
  }

  override def delete(uids: Seq[String], operator: String): Future[Int] = {
    val result = super.delete(uids, operator)

    result.map { successNum =>
      if (successNum > 0) {
        val eventSourcingRows = uids.map { uid =>
          EventSourcingRow(
            uid = UUID.randomUUID().toString,
            operatedTableName = tableName,
            operatedUid = uid,
            operation = DBOperation.Delete.toString())
        }
        eventSourcingDAO.batchInsert(eventSourcingRows)
      }
    }

    result
  }
}

object DBOperation extends Enumeration {
  val Update = Value("up")
  val Upsert = Value("us")
  val Create = Value("cr")
  val Delete = Value("de")
}
