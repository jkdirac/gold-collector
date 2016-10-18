package hupo.hedgefund.uuid

import java.util.UUID

import hupo.hedgefund.uuid.UUIDType.UUIDType
import org.apache.commons.codec.digest.DigestUtils

/**
  * Created by kunjiang on 16/8/29.
  */
object UUIDGenerator {
  def apply(uuidType: UUIDType = UUIDType.DEFAULT, input: Option[String] = None): String = {
    uuidType match {
      case UUIDType.DEFAULT => UUID.randomUUID().toString
      case UUIDType.HASH => {
        if (input.isEmpty) throw new IllegalArgumentException("Please input input string for hash type")
        //DigestUtils.md5Hex(input.get)
        input.get
      }
    }
  }
}
