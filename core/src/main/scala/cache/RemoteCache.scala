package cache

import java.nio.ByteBuffer

import scala.concurrent.duration._
import common.Logger

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}
import scalacache.serialization.Codec
import scalacache.Cache
import scala.concurrent.ExecutionContext.Implicits.global

abstract class SimpleRemoteCache() extends CascadableCache[Array[Byte]] {
  override def getFromPrivate[V](key: String): Future[Option[Array[Byte]]] = {
    Future successful None
  }

  override def removeAllPrivate(): Future[Unit] = {
    Future successful Unit
  }

  override def removePrivate(key: String): Future[Unit] = {
    Future successful Unit
  }

  override def putPrivate[V](key: String, value: Array[Byte], outDateTime: Long): Future[Unit] = {
    Future successful Unit
  }

  override def closePrivate(): Unit = {}
}

trait CascadableCache[Repr] extends RemoteCache[Repr] with Logger {
  val delegatedOpt: Option[CascadableCache[Repr]]

  def getFromPrivate[V](key: String): Future[Option[Repr]]

  def removeAllPrivate(): Future[Unit]

  def removePrivate(key: String): Future[Unit]

  def putPrivate[V](key: String, value: Repr, outDateTime: Long): Future[Unit]

  def closePrivate(): Unit

  override def get[V](key: String)(implicit codec: Codec[V, Repr]): Future[Option[V]] = {
    val dataInStorageFut = getFromPrivate(key)
    var missed = false
    val loadedResult = dataInStorageFut flatMap {
      case None => {
        missed = true
        delegatedOpt match {
          case None => Future successful None
          case Some(delegated) => delegated.get(key)
        }
      }
      case value => {
        val result = Try(value.map(codec.deserialize)) match {
          case Success(value) => value
          case Failure(e) => {
            logger.error("deserialize failed", e)
            None
          }
        }

        Future successful result
      }
    }

    loadedResult map {
      case Some(value) => if (missed) putPrivate(key, codec.serialize(value), System.currentTimeMillis() + 1.hours.toMillis)
      case None =>
    }

    loadedResult
  }

  override def removeAll(): Future[Unit] = {
    removeAll()
    delegatedOpt match {
      case None => Future successful Unit
      case Some(delegated) => delegated.removeAll()
    }
  }

  override def put[V](key: String, value: V, ttlOpt: Option[Duration] = None)(implicit codec: Codec[V, Repr]): Future[Unit] = {
    val now = System.currentTimeMillis()
    val outDateTime = ttlOpt.map(duration =>
      now + duration.toMillis
    ).getOrElse(0L)

    val serialized = codec.serialize(value)
    putPrivate(key, serialized, outDateTime)
    delegatedOpt match {
      case None => Future successful Unit
      case Some(delegated) => delegated.put(key, value, ttlOpt)
    }
  }

  override def remove(key: String): Future[Unit] = {
    removePrivate(key)
    delegatedOpt match {
      case None => Future successful Unit
      case Some(delegated) => delegated.remove(key)
    }
  }

  override def close(): Unit = {
    closePrivate()
    delegatedOpt match {
      case None => Future successful Unit
      case Some(delegated) => delegated.close()
    }
  }
}

/**
  * Created by kunjiang on 16/10/4.
  */
abstract class RemoteCache[Repr] extends Cache[Repr]{

  def assembleData(payload: Array[Byte], ttl: Long): Array[Byte] = {
    val bb = ByteBuffer.allocate(8 + payload.length)
    bb.putLong(ttl)
    bb.put(payload, 0, payload.length) // 前面8个字节是outDate的time mills

    bb.array()
  }

  def deAssembleData(data: Array[Byte]): (Array[Byte], Long) = {
    val outDateTime = ByteBuffer.wrap(data, 0, 8).getLong
    val payLoad = data.slice(8, data.length)

    (payLoad, outDateTime)
  }

  override def get[V](key: String)(implicit codec: Codec[V, Repr]): Future[Option[V]] = {
    Future successful None
  }

  override def removeAll(): Future[Unit] = {
    Future successful Unit
  }

  override def put[V](key: String, value: V, ttl: Option[Duration] = None)(implicit codec: Codec[V, Repr]): Future[Unit] = {
    Future successful Unit
  }

  override def remove(key: String): Future[Unit] = {
    Future successful Unit
  }

  override def close(): Unit = {
  }
}

object RemoteCache {
}
