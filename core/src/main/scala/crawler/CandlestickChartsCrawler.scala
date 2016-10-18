package crawler

import technique.{CandlestickChart, CandlestickPoint}

import scala.concurrent.Future

/**
  * Created by kunjiang on 16/10/18.
  */
trait CandlestickChartsCrawler {
  def crawl5min(name: String): Future[CandlestickChart]

  def crawl60min(name: String): Future[CandlestickChart]

  def crawlDay(name: String): Future[CandlestickChart]

  def crawlWeek(name: String): Future[CandlestickChart]

  def crawRealTime(name : Seq[String]) : Future[Map[String, CandlestickPoint]]
}
