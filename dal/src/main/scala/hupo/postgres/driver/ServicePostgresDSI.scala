package hupo.postgres.driver

import hupo.hedgefund.common.{EventSourcingDAO, EventSourcingDelegator}
import hupo.hedgefund.model.core._
import hupo.hedgefund.postgres.peergroup._
import hupo.hedgefund.postgres.portfolio._
import hupo.hedgefund.postgres.ugc.{FavoriteDAO, _}
import org.slf4j.LoggerFactory

trait ServicePostgresDSI {
  import hupo.postgres.driver.PostgresDriver.api._

  def env: String

  lazy val db = Database.forConfig(env)

  lazy val userDao = new ClientUserDAO(this) with EventSourcingDelegator[ClientUserRow]
  lazy val clientCompanyDao = new ClientCompanyDAO(this) with EventSourcingDelegator[ClientCompanyRow]

  lazy val hfCompanyDao = new HfCompanyPrivateDAO(this) with EventSourcingDelegator[HfCompanyRow]
  lazy val hfCompanyResearchDao = new HFCompanyResearchDAO(this) with EventSourcingDelegator[HfCompanyResearchRow]
  lazy val hfProductPrivateDAO = new HfProductPrivateDAO(this) with EventSourcingDelegator[HfProductPrivateRow]
  lazy val hfProductResearchDAO = new HfProductResearchDAO(this) with EventSourcingDelegator[HfProductResearchRow]
  lazy val hfProductNetValueDAO = new HfProductNavPrivateDAO(this) with EventSourcingDelegator[HfProductNavPrivateRow]

  lazy val commentsDao = new CommentsDAO(this) with EventSourcingDelegator[CommentsRow]
  lazy val favoritesDao = new FavoriteDAO(this) with EventSourcingDelegator[FavoriteRow]
  lazy val viewHistoryDao = new ViewHistoryDAO(this)

  //lazy val fundExecutiveDao = new FundExecutivesDAO(this) with EventSourcingDelegator[FundExecutivesRow]
  lazy val finalReviewDao = new DueDiligenceFinalReviewDAO(this) with EventSourcingDelegator[DueDiligenceFinalReviewRow]
  lazy val reviewDao = new DueDiligenceReviewDAO(this) with EventSourcingDelegator[DueDiligenceReviewRow]
  lazy val reportDao = new DueDiligenceReportDAO(this) with EventSourcingDelegator[DueDiligenceReportRow]
  lazy val taskDao = new TaskPgDAO(this) with EventSourcingDelegator[TaskRow]
  lazy val benchmarkDAO = new BenchmarkDAO(this) with EventSourcingDelegator[BenchmarkRow]
  lazy val peerGroupDAO = new PeerGroupDAO(this) with EventSourcingDelegator[PeerGroupRow]
  lazy val groupProductRDAO = new GroupProductRDAO(this) with EventSourcingDelegator[GroupProductRRow]

  lazy val portfolioDAO = new PortfolioDAO(this) with EventSourcingDelegator[PortfolioRow]
  lazy val portfolioTurningHistoryDAO = new PortfolioTuningHistoryDAO(this) with EventSourcingDelegator[PortfolioTuningHistoryRow]
  lazy val portfolioProductRDAO = new PortfolioProductRDAO(this) with EventSourcingDelegator[PortfolioProductRRow]
  lazy val portfolioPerformanceDAO = new PortfolioPerformanceDAO(this) with EventSourcingDelegator[PortfolioPerformanceRow]
  lazy val portfolioTransactionDAO = new PortfolioTransactionHistoryDAO(this) with EventSourcingDelegator[PortfolioTransactionHistoryRow]
  lazy val portfolioNAVHistoryDAO = new PortfolioNAVHistoryDAO(this) with EventSourcingDelegator[PortfolioNavHistoryRow]
  lazy val portfolioMonitorJobDAO = new HfPortfolioMonitorJobDAO(this) with EventSourcingDelegator[HfPortfolioMonitorJobRow]
  lazy val portfolioMonitorTriggerHistoryDAO = new HfPortfolioMonitorTriggerHistoryDAO(this) with EventSourcingDelegator[HfPortfolioMonitorTriggerHistoryRow]
  lazy val indicatorDAO = new IndicatorDAO(this) with EventSourcingDelegator[IndicatorRow]

  lazy val eventSourcingDAO = new EventSourcingDAO(this) //don't mixin EventSourcingDelegator for it
}

object ServiceDSI extends ServicePostgresDSI {
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