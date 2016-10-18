package cache

import com.google.common.cache.CacheBuilder

import scala.concurrent.Future

/**
  * Created by kunjiang on 16/9/24.
  */
class MemoryCache(val delegatedOpt: Option[CascadableCache[Array[Byte]]] = None) extends CascadableCache[Array[Byte]] {
  val guavaCache = CacheBuilder.newBuilder().build[String, Array[Byte]]()

  override def getFromPrivate[V](key: String): Future[Option[Array[Byte]]] = {
    val dataInStorage = guavaCache.getIfPresent(key)
    val result = if (dataInStorage == null) {
      None
    } else {
      val (payLoad, outDateTime) = deAssembleData(dataInStorage)
      if (outDateTime > 0 && System.currentTimeMillis() > outDateTime) {
        removePrivate(key)
        None
      } else {
        Some(payLoad)
      }
    }

    Future successful result
  }

  override def removeAllPrivate(): Future[Unit] = {
    Future successful guavaCache.invalidateAll()
  }

  override def removePrivate(key: String): Future[Unit] = {
    Future successful guavaCache.invalidate(key)
  }

  override def putPrivate[V](key: String, value: Array[Byte], outDateTimeMills: Long): Future[Unit] = {
    Future successful guavaCache.put(key, assembleData(value, outDateTimeMills))
  }

  override def closePrivate(): Unit = {}
}
