package hupo.hedgefund.uuid

/**
  * Created by kunjiang on 16/8/29.
  */
object UUIDType extends Enumeration {
  type UUIDType = Value

  val DEFAULT = Value("Default")  // java random hupo.hedgefund.uuid
  val HASH = Value("Hash")        // 使用某个字符串组合的hash值产生的uuid
}
