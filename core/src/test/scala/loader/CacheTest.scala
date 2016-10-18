package loader

import cache.{FileRemoteCache, MemoryCache}
import org.junit.{Assert, Test}

import scala.concurrent.duration._
import scalacache.serialization.Codec
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class CacheTest {
  case class CacheKey(key: String)
  case class CacheValue(item1: String, item2: String)
  implicit val codeC = new Codec[CacheValue, Array[Byte]] {
    override def serialize(value: CacheValue): Array[Byte] = {
      value.asJson.spaces2.getBytes("UTF-8")
    }

    override def deserialize(data: Array[Byte]): CacheValue = {
      val jsonString = new String(data, "UTF-8")
      decode[CacheValue](jsonString).toEither match {
        case Right(obj) => obj
        case Left(e) => throw new IllegalArgumentException(s"Invalid json input $jsonString")
      }
    }
  }

  @Test
  def memoryCacheTest(): Unit = {
    val memoryCache = new MemoryCache(None)
    val cacheKey = CacheKey("key").toString
    val cacheValue1 = CacheValue("item1", "item1")
    Await.result(memoryCache.put(cacheKey, cacheValue1), Duration.Inf)

    val loadedCache1 = Await.result(memoryCache.get(cacheKey), Duration.Inf)
    Assert.assertEquals(cacheValue1, loadedCache1.get)

    val cacheValue2 = CacheValue("item2", "item2")
    Await.result(memoryCache.put(cacheKey, cacheValue2, Some(10.millisecond)), Duration.Inf)
    val loadedCache2 = Await.result(memoryCache.get(cacheKey), Duration.Inf)
    Assert.assertEquals(cacheValue2, loadedCache2.get)

    Thread.sleep(10)
    val loadedTimeOutedOpt = Await.result(memoryCache.get(cacheKey), Duration.Inf)
    Assert.assertTrue(loadedTimeOutedOpt.isEmpty)
  }

  @Test
  def memoryFileCacheTest(): Unit = {
    val cache = new FileRemoteCache(Some(new MemoryCache))

    val cacheKey = CacheKey("key").toString
    val cacheValue1 = CacheValue("item1", "item1")
    Await.result(cache.put(cacheKey, cacheValue1), Duration.Inf)
    //级联的cache中都存在这个记录
    val loadedCachePrivate = Await.result(cache.getFromPrivate(cacheKey), Duration.Inf)
    Assert.assertTrue(loadedCachePrivate.isDefined)
    val loadedCachePrivateFromDelegated = Await.result(cache.delegatedOpt.get.getFromPrivate(cacheKey), Duration.Inf)
    Assert.assertTrue(loadedCachePrivateFromDelegated.isDefined)

    cache.delegatedOpt.get.removePrivate(cacheKey) //删除级联中的一个,依然能获取到
    val loadedCache1 = Await.result(cache.get(cacheKey), Duration.Inf)
    Assert.assertEquals(cacheValue1, loadedCache1.get)

    val cacheValue2 = CacheValue("item2", "item2")
    Await.result(cache.put(cacheKey, cacheValue2, Some(10.millisecond)), Duration.Inf)  //测试过期时间
    val loadedCache2 = Await.result(cache.get(cacheKey), Duration.Inf)
    Assert.assertEquals(cacheValue2, loadedCache2.get)

    Thread.sleep(10)
    val loadedTimeOutedOpt = Await.result(cache.get(cacheKey), Duration.Inf)
    Assert.assertTrue(loadedTimeOutedOpt.isEmpty)   //已经过期
  }
}

