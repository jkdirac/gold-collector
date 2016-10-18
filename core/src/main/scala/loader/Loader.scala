package loader

import cache.CascadableCache
import common.Logger

import scalacache._
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scalacache.ScalaCache
import scalacache.serialization.Codec
import scala.concurrent.ExecutionContext.Implicits.global

trait LoadedKey {
  val id: String
}

trait LoadedValue

/**
  * Created by kunjiang on 16/10/4.
  */
abstract class Loader[From <: LoadedKey, To <: LoadedValue] {
  def load(f: From): Future[To] = {
    throw new UnsupportedOperationException("Please override this method")
  }
}

class CacheAbleLoader[From <: LoadedKey, To <: LoadedValue](
  private val loader: Loader[From, To],
  private val cache: CascadableCache[Array[Byte]],
  private val duration: Duration)(implicit codec: Codec[To, Array[Byte]]) extends Loader[From, To] with Logger {

  logger.info(s"Use ${codec.getClass} as codeC ")
  implicit val scalaCache = ScalaCache(cache)

  override def load(f: From): Future[To] = {
    cachingWithTTL(f.toString)(duration) {
      loader.load(f)
    }
  }

  def initCache(): Future[Unit] = {
    Future successful Unit
  }
}

trait MonitoringLoader[From <: LoadedKey, To <: LoadedValue] extends Loader[From, To] with Logger {
  override def load(f: From): Future[To] = {
    val startTime = System.currentTimeMillis()
    super.load(f).map(result => {
      val endTime = System.currentTimeMillis()

      val latency = endTime - startTime;
      if (latency > 50) {
        logger.info(s"Load ${f.id}: ${f.toString} takes ${latency}ms")
      }

      result
    })
  }
}
