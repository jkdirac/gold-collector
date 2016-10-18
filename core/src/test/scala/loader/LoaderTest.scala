package loader

import cache.MemoryCache
import org.junit.{Assert, Test}
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scalacache.ScalaCache
import scalacache.serialization.Codec

/**
  * Created by kunjiang on 16/10/4.
  */
class LoaderTest {
  case class TestCacheKey(key: String) extends LoadedKey {
    override val id: String = "test"
  }
  case class TestValue(value: String) extends LoadedValue
  implicit val codeC = new Codec[TestValue, Array[Byte]] {
    override def serialize(value: TestValue): Array[Byte] = {
      value.asJson.spaces2.getBytes("UTF-8")
    }

    override def deserialize(data: Array[Byte]): TestValue = {
      val jsonString = new String(data, "UTF-8")
      decode[TestValue](jsonString).toEither match {
        case Right(obj) => obj
        case Left(e) => throw new IllegalArgumentException(s"Invalid json input $jsonString")
      }
    }
  }

  @Test
  def cacheAbleLoaderTest(): Unit = {
    val memoryCache = new MemoryCache(None)
    val loader = new Loader[TestCacheKey, TestValue] {
      override def load(f: TestCacheKey): Future[TestValue] = {
        Future successful TestValue(f.key)
      }
    }

    val cachedLoader = new CacheAbleLoader[TestCacheKey, TestValue] (loader, memoryCache, 1.hours)
    val result = Await.result(cachedLoader.load(TestCacheKey("key")), Duration.Inf)
    Assert.assertEquals(result.value, "key")
  }
}
