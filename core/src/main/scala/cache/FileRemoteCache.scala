package cache

import java.io.File

import scala.concurrent.Future
import scalax.file.Path
import scalax.io.Resource

/**
  * Created by kunjiang on 16/9/24.
  */
class FileRemoteCache(val delegatedOpt: Option[CascadableCache[Array[Byte]]] = None) extends CascadableCache[Array[Byte]] {
  var rootPath = "cache"
  val rootDirectory = Path(rootPath)
  if (!rootDirectory.exists) {
    rootDirectory.createDirectory() //创建对象
  }

  override def getFromPrivate[V](key: String): Future[Option[Array[Byte]]] = {
    val keyPath = Path(rootPath) / key
    logger.info(s"Loaded ${key} from file cache ${keyPath.toAbsolute.toString()}")
    val result = if (!keyPath.exists) {
      None
    } else {
      val dataInStorage = Resource.fromFile(keyPath.jfile).byteArray
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
    val file = new File(rootPath)
    file.listFiles().map(_.delete())
    Future successful Unit
  }

  override def removePrivate(key: String): Future[Unit] = {
    val path = Path(rootPath) / key
    Future successful path.delete(true)
  }

  override def putPrivate[V](key: String, value: Array[Byte], outDateTimeMills: Long): Future[Unit] = {
    val path = Path(rootPath) / key
    path.delete(true)

    if (value.length > 1024) {
      logger.info(s"Cache ${value.length} Bytes for ${key}")
    }
    path.write(assembleData(value, outDateTimeMills))

    Future successful Unit
  }

  override def closePrivate(): Unit = {}
}
