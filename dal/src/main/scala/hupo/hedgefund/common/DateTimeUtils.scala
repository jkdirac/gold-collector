package hupo.hedgefund.common

import java.sql.{Date, Timestamp}
import java.time.format.DateTimeFormatter
import java.time._

import play.api.data.format.Formats
import play.api.libs.json.{JsString, JsValue, Writes}

import scala.concurrent.duration.Duration
import scala.concurrent.duration._

object DateTimeUtils {
  val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  def sqlDateString(date: Date): String = s"${date.toString}T00:00:00.000Z"
  def localDateTimeString(dateTime: LocalDateTime): String = s"${dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}.000Z"
  def zonedDateString(dateTime: ZonedDateTime): String = dateTime.toInstant.toString
  def timeStampString(dateTime: Timestamp): String = dateTime.toInstant.toString
  def dateOutput(date: String): String = s"${date}T00:00:00.000Z"

  implicit val dateWrites = Writes.dateWrites("yyyy-MM-dd'T'00:00:00.000'Z'")
  implicit val timeStampWrite = new Writes[Timestamp] {
    override def writes(o: Timestamp): JsValue = JsString(timeStampString(o))
  }
  implicit val dateFormats = Formats.dateFormat("yyyy-MM-dd'T'00:00:00.000'Z'")

  // inclusive
  def dateRange(from: LocalDateTime, to: LocalDateTime, step: Duration = 1.days): Iterator[LocalDateTime] = {
    Iterator.iterate(from)(_.plusDays(step.toDays)).takeWhile(!_.isAfter(to))
  }

  def daysBetween(start: LocalDate, end: LocalDate): Long = {
    end.toEpochDay - start.toEpochDay
  }

  def stringZonedDateTime(date: String): ZonedDateTime = {
    val dataZonedDatetime = ZonedDateTime.parse(date)
    dataZonedDatetime
  }

  def date2LocalDate(date: Date): LocalDate = {
    Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
  }

  def localDateRange(from: LocalDate, to: LocalDate, step: Duration = 1.days): Iterator[LocalDate] = {
    Iterator.iterate(from)(_.plusDays(step.toDays)).takeWhile(!_.isAfter(to))
  }
}
