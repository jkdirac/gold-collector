package technique

import java.time.LocalDateTime

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration

case class CandlestickPoint(dateTime: LocalDateTime, open: Double, close: Double, high: Double, low: Double, change: Double, volume: Long)

/**
 * @author jixu
 */
case class CandlestickChart(code: String, endTime: LocalDateTime, duration: Duration, points: Seq[CandlestickPoint]) {
  def append(point : CandlestickPoint) : CandlestickChart = {
    // store by datetime in descend time
    val sortedPoints = this.points.sortWith((p1, p2) => p1.dateTime.isAfter(p2.dateTime))
    if (sortedPoints.nonEmpty && sortedPoints.head.dateTime.equals(point.dateTime)) {
      point +: sortedPoints.drop(1)
    } else {
      point +: sortedPoints
    }

    this.copy(endTime = sortedPoints.head.dateTime, points = sortedPoints)
  }

  def subChart(start: Int): CandlestickChart = {
    CandlestickChart(code, points(start).dateTime, duration, points.drop(start))
  }

  /**
   * @param start inclusive
   * @param end exclusive
   * @return
   */
  def subChart(start: Int, end: Int): CandlestickChart = {
    val points = this.points.slice(start, end + 1)
    if (points.isEmpty) {
      this.copy(points = Seq())
    } else {
      this.copy(endTime = points.head.dateTime, points = points)
    }
  }

  def getPoint(index: Int): CandlestickPoint = {
    points(index)
  }

  def getPoint(time: LocalDateTime): CandlestickPoint = {
    points.find(point => point.dateTime.equals(time)).get;
  }

  def getPointIndex(time: LocalDateTime): Int = {
      points.indexWhere(point => point.dateTime.equals(time))
  }
}
