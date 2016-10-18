package hupo.postgres.driver

import hupo.hedgefund.postgres.cache.{HfLatestEvaluationCacheDAO, HfLatestEvaluationMergeCacheDAO}
import hupo.hedgefund.postgres.crawled._
import hupo.hedgefund.postgres.evaluation._
import hupo.hedgefund.postgres.ugc.FundExecutivesDAO
import org.slf4j.LoggerFactory

trait CrawlerPostgresDSI {
  import hupo.postgres.driver.PostgresDriver.api._

  def env: String

  lazy val db = Database.forConfig(env)

  lazy val hfCompanyMergeDAO = new HFCompanyMergeDAO(this)
  lazy val hfCompanyMergeStatisticDAO = new HfCompanyStatisticInfoDAO(this)

  lazy val hfProductMergeDao = new HFProductMergeDAO(this)
  lazy val hfManagerProductMergeDao = new HfManagerProductMergeDAO(this)

  lazy val fundExecutiveDao = new HFExecutivesMergeDAO(this)
  lazy val fundRobustFitDao = new HfFundRobustFitDAO(this)

  lazy val hfNavMergeDao = new HfNavMergeDao(this)
  lazy val hfProductCacheDao = new HfLatestEvaluationMergeCacheDAO(this)

  // evaluation
  lazy val fundReturnYieldYearlyDao = new HfFundReturnYieldYearDao(this)
  lazy val hfFundReturnDAO = new HfFundReturnDAO(this)
  lazy val fundEvaluationDao = new HfFundEvaluationDAO(this)
  lazy val hfHistoryPerformanceDao = new HfHistoryPerformanceDAO(this)
  lazy val hfRobustFitDao = new HfFundRobustFitDAO(this)
  lazy val hfEvaluationBenchMarkDao = new HfEvaluationBenchmarkDAO(this)
  lazy val hfUpDownPerformanceDAO = new HfUpDownPerformanceMergeDAO(this)
  lazy val hfHomoCodeDAO = new HfHomoCodeMergeDAO(this)

  lazy val vFundChangeDataDAO = new VFundChangeDataDAO(this)
  lazy val vFundSplitDataDAO = new VFundSplitDataDAO(this)
  lazy val vHfIdCodeRDAO = new VHfIdCodeRDAO(this)
  lazy val hDataIndexDAO = new HDataIndexDAO(this)
  lazy val hfStyleExposureDAO = new HfStyleExposureDAO(this)

  //open_day
  lazy val hfOpenDateDAO = new HfOpenDateDAO(this)
  lazy val HfLiquidityDAO = new HfLiquidityDAO(this)

}

object CrawlerDSI extends CrawlerPostgresDSI {
  private var _env: String = null
  private def logger = LoggerFactory.getLogger(this.getClass)


  def env = if (_env == null) "dev" else _env

  def init(e: String) = synchronized {
    if (_env == null) {
      _env = e
      db
    } else {
      throw new IllegalArgumentException(s"${_env} has been init")
    }
  }

  def initForTest(e: String) = synchronized {
    if (_env == null) {
      _env = e
      db
    }
  }
}