package hupo.hedgefund.common

import java.time.ZonedDateTime
import java.util.UUID

import play.api.libs.json.JsValue

trait Entity extends Product {
  def uuid: UUID = UUID.fromString(uid)

  def uid: String

  def isDeleted: Boolean

  def createdAt: ZonedDateTime

  def updatedAt: ZonedDateTime

  def toJson(): JsValue
}

