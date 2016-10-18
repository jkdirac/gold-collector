package hupo.generated
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.collection.heterogeneous._
  import slick.collection.heterogeneous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(ClientCompany.schema, ClientUser.schema, DueDiligenceFinalReview.schema, DueDiligenceReport.schema, DueDiligenceReview.schema, FundExecutives.schema, FundManager.schema, HedgefundPerforamnce.schema, HedgeFundProductFee.schema, HedgeFundProductInfo.schema, HedgeFundProductNetValue.schema, HedgeFundStrategyInfo.schema, HlistTestTable.schema, HupoUser.schema, HupoUserRolePermission.schema, InvestmentTeam.schema, LegalEntity.schema, OtcHedgeFundSecurity.schema, PlayEvolutions.schema, Task.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table ClientCompany
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param name Database column name SqlType(varchar), Length(48,true)
   *  @param roleNames Database column role_names SqlType(_text), Length(2147483647,false), Default(None)
   *  @param telephone Database column telephone SqlType(varchar), Length(48,true), Default(None)
   *  @param mail Database column mail SqlType(varchar), Length(48,true), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class ClientCompanyRow(uid: String, name: String, roleNames: Option[String] = None, telephone: Option[String] = None, mail: Option[String] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ClientCompanyRow objects using plain SQL queries */
  implicit def GetResultClientCompanyRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[ClientCompanyRow] = GR{
    prs => import prs._
    ClientCompanyRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table client_company. Objects of this class serve as prototypes for rows in queries. */
  class ClientCompany(_tableTag: Tag) extends Table[ClientCompanyRow](_tableTag, "client_company") {
    def * = (uid, name, roleNames, telephone, mail, editedBy, isDeleted, createdAt, updatedAt) <> (ClientCompanyRow.tupled, ClientCompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(name), roleNames, telephone, mail, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ClientCompanyRow.tupled((_1.get, _2.get, _3, _4, _5, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column name SqlType(varchar), Length(48,true) */
    val name: Rep[String] = column[String]("name", O.Length(48,varying=true))
    /** Database column role_names SqlType(_text), Length(2147483647,false), Default(None) */
    val roleNames: Rep[Option[String]] = column[Option[String]]("role_names", O.Length(2147483647,varying=false), O.Default(None))
    /** Database column telephone SqlType(varchar), Length(48,true), Default(None) */
    val telephone: Rep[Option[String]] = column[Option[String]]("telephone", O.Length(48,varying=true), O.Default(None))
    /** Database column mail SqlType(varchar), Length(48,true), Default(None) */
    val mail: Rep[Option[String]] = column[Option[String]]("mail", O.Length(48,varying=true), O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table ClientCompany */
  lazy val ClientCompany = new TableQuery(tag => new ClientCompany(tag))

  /** Entity class storing rows of table ClientUser
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param companyUid Database column company_uid SqlType(varchar), Length(48,true)
   *  @param nickName Database column nick_name SqlType(varchar), Length(48,true), Default(None)
   *  @param lastName Database column last_name SqlType(varchar), Length(8,true), Default(None)
   *  @param firstName Database column first_name SqlType(varchar), Length(8,true), Default(None)
   *  @param email Database column email SqlType(varchar), Length(48,true), Default(None)
   *  @param mobile Database column mobile SqlType(varchar), Length(48,true), Default(None)
   *  @param roleNames Database column role_names SqlType(_text), Length(2147483647,false), Default(None)
   *  @param password Database column password SqlType(varchar), Length(1024,true), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class ClientUserRow(uid: String, companyUid: String, nickName: Option[String] = None, lastName: Option[String] = None, firstName: Option[String] = None, email: Option[String] = None, mobile: Option[String] = None, roleNames: Option[String] = None, password: Option[String] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ClientUserRow objects using plain SQL queries */
  implicit def GetResultClientUserRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[ClientUserRow] = GR{
    prs => import prs._
    ClientUserRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table client_user. Objects of this class serve as prototypes for rows in queries. */
  class ClientUser(_tableTag: Tag) extends Table[ClientUserRow](_tableTag, "client_user") {
    def * = (uid, companyUid, nickName, lastName, firstName, email, mobile, roleNames, password, editedBy, isDeleted, createdAt, updatedAt) <> (ClientUserRow.tupled, ClientUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(companyUid), nickName, lastName, firstName, email, mobile, roleNames, password, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ClientUserRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10.get, _11.get, _12.get, _13.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column company_uid SqlType(varchar), Length(48,true) */
    val companyUid: Rep[String] = column[String]("company_uid", O.Length(48,varying=true))
    /** Database column nick_name SqlType(varchar), Length(48,true), Default(None) */
    val nickName: Rep[Option[String]] = column[Option[String]]("nick_name", O.Length(48,varying=true), O.Default(None))
    /** Database column last_name SqlType(varchar), Length(8,true), Default(None) */
    val lastName: Rep[Option[String]] = column[Option[String]]("last_name", O.Length(8,varying=true), O.Default(None))
    /** Database column first_name SqlType(varchar), Length(8,true), Default(None) */
    val firstName: Rep[Option[String]] = column[Option[String]]("first_name", O.Length(8,varying=true), O.Default(None))
    /** Database column email SqlType(varchar), Length(48,true), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Length(48,varying=true), O.Default(None))
    /** Database column mobile SqlType(varchar), Length(48,true), Default(None) */
    val mobile: Rep[Option[String]] = column[Option[String]]("mobile", O.Length(48,varying=true), O.Default(None))
    /** Database column role_names SqlType(_text), Length(2147483647,false), Default(None) */
    val roleNames: Rep[Option[String]] = column[Option[String]]("role_names", O.Length(2147483647,varying=false), O.Default(None))
    /** Database column password SqlType(varchar), Length(1024,true), Default(None) */
    val password: Rep[Option[String]] = column[Option[String]]("password", O.Length(1024,varying=true), O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Foreign key referencing ClientCompany (database name client_user_company_uid_fkey) */
    lazy val clientCompanyFk = foreignKey("client_user_company_uid_fkey", companyUid, ClientCompany)(r => r.uid, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    /** Index over (createdAt) (database name client_user_created_at_idx) */
    val index1 = index("client_user_created_at_idx", createdAt)
    /** Uniqueness Index over (email) (database name client_user_email_key) */
    val index2 = index("client_user_email_key", email, unique=true)
    /** Index over (firstName) (database name client_user_first_name_idx) */
    val index3 = index("client_user_first_name_idx", firstName)
    /** Index over (lastName) (database name client_user_last_name_idx) */
    val index4 = index("client_user_last_name_idx", lastName)
    /** Uniqueness Index over (mobile) (database name client_user_mobile_key) */
    val index5 = index("client_user_mobile_key", mobile, unique=true)
    /** Uniqueness Index over (nickName) (database name client_user_nick_name_key) */
    val index6 = index("client_user_nick_name_key", nickName, unique=true)
    /** Index over (updatedAt) (database name client_user_updated_at_idx) */
    val index7 = index("client_user_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table ClientUser */
  lazy val ClientUser = new TableQuery(tag => new ClientUser(tag))

  /** Entity class storing rows of table DueDiligenceFinalReview
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param reportUid Database column report_uid SqlType(varchar)
   *  @param status Database column status SqlType(varchar)
   *  @param comments Database column comments SqlType(jsonb), Length(2147483647,false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class DueDiligenceFinalReviewRow(uid: String, reportUid: String, status: String, comments: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching DueDiligenceFinalReviewRow objects using plain SQL queries */
  implicit def GetResultDueDiligenceFinalReviewRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[DueDiligenceFinalReviewRow] = GR{
    prs => import prs._
    DueDiligenceFinalReviewRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table due_diligence_final_review. Objects of this class serve as prototypes for rows in queries. */
  class DueDiligenceFinalReview(_tableTag: Tag) extends Table[DueDiligenceFinalReviewRow](_tableTag, "due_diligence_final_review") {
    def * = (uid, reportUid, status, comments, createdAt, updatedAt) <> (DueDiligenceFinalReviewRow.tupled, DueDiligenceFinalReviewRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(reportUid), Rep.Some(status), Rep.Some(comments), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> DueDiligenceFinalReviewRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column report_uid SqlType(varchar) */
    val reportUid: Rep[String] = column[String]("report_uid")
    /** Database column status SqlType(varchar) */
    val status: Rep[String] = column[String]("status")
    /** Database column comments SqlType(jsonb), Length(2147483647,false) */
    val comments: Rep[String] = column[String]("comments", O.Length(2147483647,varying=false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Foreign key referencing DueDiligenceReport (database name reporter_fk) */
    lazy val dueDiligenceReportFk = foreignKey("reporter_fk", reportUid, DueDiligenceReport)(r => r.uid, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    /** Index over (createdAt) (database name due_diligence_final_review_created_at_idx) */
    val index1 = index("due_diligence_final_review_created_at_idx", createdAt)
    /** Uniqueness Index over (reportUid) (database name due_diligence_final_review_report_uid_idx) */
    val index2 = index("due_diligence_final_review_report_uid_idx", reportUid, unique=true)
    /** Index over (updatedAt) (database name due_diligence_final_review_updated_at_idx) */
    val index3 = index("due_diligence_final_review_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table DueDiligenceFinalReview */
  lazy val DueDiligenceFinalReview = new TableQuery(tag => new DueDiligenceFinalReview(tag))

  /** Entity class storing rows of table DueDiligenceReport
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param teamUid Database column team_uid SqlType(varchar), Length(64,true)
   *  @param status Database column status SqlType(varchar), Length(8,true)
   *  @param reportReason Database column report_reason SqlType(varchar), Length(16,true)
   *  @param investmentStyle Database column investment_style SqlType(varchar), Length(1024,true)
   *  @param developmentStage Database column development_stage SqlType(varchar), Length(256,true)
   *  @param teamStyle Database column team_style SqlType(varchar), Length(256,true)
   *  @param ltPerformance Database column lt_performance SqlType(varchar), Length(1024,true)
   *  @param stPerformance Database column st_performance SqlType(varchar), Length(1024,true)
   *  @param ifStpLuck Database column if_stp_luck SqlType(varchar), Length(64,true)
   *  @param stPoorPerformanceReason Database column st_poor_performance_reason SqlType(varchar), Length(256,true)
   *  @param ifPerformanceConformPhilosophy Database column if_performance_conform_philosophy SqlType(varchar), Length(64,true)
   *  @param ifProductsDiversified Database column if_products_diversified SqlType(varchar), Length(64,true)
   *  @param ifIndicatorChange Database column if_indicator_change SqlType(varchar), Length(64,true)
   *  @param profitCycle Database column profit_cycle SqlType(varchar), Length(256,true)
   *  @param profitCore Database column profit_core SqlType(varchar), Length(256,true)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class DueDiligenceReportRow(uid: String, teamUid: String, status: String, reportReason: String, investmentStyle: String, developmentStage: String, teamStyle: String, ltPerformance: String, stPerformance: String, ifStpLuck: String, stPoorPerformanceReason: String, ifPerformanceConformPhilosophy: String, ifProductsDiversified: String, ifIndicatorChange: String, profitCycle: String, profitCore: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching DueDiligenceReportRow objects using plain SQL queries */
  implicit def GetResultDueDiligenceReportRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[DueDiligenceReportRow] = GR{
    prs => import prs._
    DueDiligenceReportRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table due_diligence_report. Objects of this class serve as prototypes for rows in queries. */
  class DueDiligenceReport(_tableTag: Tag) extends Table[DueDiligenceReportRow](_tableTag, "due_diligence_report") {
    def * = (uid, teamUid, status, reportReason, investmentStyle, developmentStage, teamStyle, ltPerformance, stPerformance, ifStpLuck, stPoorPerformanceReason, ifPerformanceConformPhilosophy, ifProductsDiversified, ifIndicatorChange, profitCycle, profitCore, createdAt, updatedAt) <> (DueDiligenceReportRow.tupled, DueDiligenceReportRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(teamUid), Rep.Some(status), Rep.Some(reportReason), Rep.Some(investmentStyle), Rep.Some(developmentStage), Rep.Some(teamStyle), Rep.Some(ltPerformance), Rep.Some(stPerformance), Rep.Some(ifStpLuck), Rep.Some(stPoorPerformanceReason), Rep.Some(ifPerformanceConformPhilosophy), Rep.Some(ifProductsDiversified), Rep.Some(ifIndicatorChange), Rep.Some(profitCycle), Rep.Some(profitCore), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> DueDiligenceReportRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column team_uid SqlType(varchar), Length(64,true) */
    val teamUid: Rep[String] = column[String]("team_uid", O.Length(64,varying=true))
    /** Database column status SqlType(varchar), Length(8,true) */
    val status: Rep[String] = column[String]("status", O.Length(8,varying=true))
    /** Database column report_reason SqlType(varchar), Length(16,true) */
    val reportReason: Rep[String] = column[String]("report_reason", O.Length(16,varying=true))
    /** Database column investment_style SqlType(varchar), Length(1024,true) */
    val investmentStyle: Rep[String] = column[String]("investment_style", O.Length(1024,varying=true))
    /** Database column development_stage SqlType(varchar), Length(256,true) */
    val developmentStage: Rep[String] = column[String]("development_stage", O.Length(256,varying=true))
    /** Database column team_style SqlType(varchar), Length(256,true) */
    val teamStyle: Rep[String] = column[String]("team_style", O.Length(256,varying=true))
    /** Database column lt_performance SqlType(varchar), Length(1024,true) */
    val ltPerformance: Rep[String] = column[String]("lt_performance", O.Length(1024,varying=true))
    /** Database column st_performance SqlType(varchar), Length(1024,true) */
    val stPerformance: Rep[String] = column[String]("st_performance", O.Length(1024,varying=true))
    /** Database column if_stp_luck SqlType(varchar), Length(64,true) */
    val ifStpLuck: Rep[String] = column[String]("if_stp_luck", O.Length(64,varying=true))
    /** Database column st_poor_performance_reason SqlType(varchar), Length(256,true) */
    val stPoorPerformanceReason: Rep[String] = column[String]("st_poor_performance_reason", O.Length(256,varying=true))
    /** Database column if_performance_conform_philosophy SqlType(varchar), Length(64,true) */
    val ifPerformanceConformPhilosophy: Rep[String] = column[String]("if_performance_conform_philosophy", O.Length(64,varying=true))
    /** Database column if_products_diversified SqlType(varchar), Length(64,true) */
    val ifProductsDiversified: Rep[String] = column[String]("if_products_diversified", O.Length(64,varying=true))
    /** Database column if_indicator_change SqlType(varchar), Length(64,true) */
    val ifIndicatorChange: Rep[String] = column[String]("if_indicator_change", O.Length(64,varying=true))
    /** Database column profit_cycle SqlType(varchar), Length(256,true) */
    val profitCycle: Rep[String] = column[String]("profit_cycle", O.Length(256,varying=true))
    /** Database column profit_core SqlType(varchar), Length(256,true) */
    val profitCore: Rep[String] = column[String]("profit_core", O.Length(256,varying=true))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name due_diligence_report_created_at_idx) */
    val index1 = index("due_diligence_report_created_at_idx", createdAt)
    /** Uniqueness Index over (teamUid) (database name due_diligence_report_team_uid_idx) */
    val index2 = index("due_diligence_report_team_uid_idx", teamUid, unique=true)
    /** Index over (updatedAt) (database name due_diligence_report_updated_at_idx) */
    val index3 = index("due_diligence_report_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table DueDiligenceReport */
  lazy val DueDiligenceReport = new TableQuery(tag => new DueDiligenceReport(tag))

  /** Entity class storing rows of table DueDiligenceReview
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param authorUid Database column author_uid SqlType(varchar)
   *  @param reportUid Database column report_uid SqlType(varchar)
   *  @param comment Database column comment SqlType(varchar)
   *  @param ratings Database column ratings SqlType(hstore), Length(2147483647,false)
   *  @param status Database column status SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class DueDiligenceReviewRow(uid: String, authorUid: String, reportUid: String, comment: String, ratings: String, status: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching DueDiligenceReviewRow objects using plain SQL queries */
  implicit def GetResultDueDiligenceReviewRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[DueDiligenceReviewRow] = GR{
    prs => import prs._
    DueDiligenceReviewRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table due_diligence_review. Objects of this class serve as prototypes for rows in queries. */
  class DueDiligenceReview(_tableTag: Tag) extends Table[DueDiligenceReviewRow](_tableTag, "due_diligence_review") {
    def * = (uid, authorUid, reportUid, comment, ratings, status, createdAt, updatedAt) <> (DueDiligenceReviewRow.tupled, DueDiligenceReviewRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(authorUid), Rep.Some(reportUid), Rep.Some(comment), Rep.Some(ratings), Rep.Some(status), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> DueDiligenceReviewRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column author_uid SqlType(varchar) */
    val authorUid: Rep[String] = column[String]("author_uid")
    /** Database column report_uid SqlType(varchar) */
    val reportUid: Rep[String] = column[String]("report_uid")
    /** Database column comment SqlType(varchar) */
    val comment: Rep[String] = column[String]("comment")
    /** Database column ratings SqlType(hstore), Length(2147483647,false) */
    val ratings: Rep[String] = column[String]("ratings", O.Length(2147483647,varying=false))
    /** Database column status SqlType(varchar) */
    val status: Rep[String] = column[String]("status")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Foreign key referencing DueDiligenceReport (database name reporter_fk) */
    lazy val dueDiligenceReportFk = foreignKey("reporter_fk", reportUid, DueDiligenceReport)(r => r.uid, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    /** Index over (createdAt) (database name due_diligence_review_created_at_idx) */
    val index1 = index("due_diligence_review_created_at_idx", createdAt)
    /** Index over (updatedAt) (database name due_diligence_review_updated_at_idx) */
    val index2 = index("due_diligence_review_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table DueDiligenceReview */
  lazy val DueDiligenceReview = new TableQuery(tag => new DueDiligenceReview(tag))

  /** Entity class storing rows of table FundExecutives
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param employerUuid Database column employer_uuid SqlType(varchar), Length(48,true)
   *  @param name Database column name SqlType(varchar), Length(16,true)
   *  @param status Database column status SqlType(varchar), Length(64,true), Default(None)
   *  @param workingYears Database column working_years SqlType(int4), Default(None)
   *  @param workingExperience Database column working_experience SqlType(hstore), Length(2147483647,false), Default(None)
   *  @param gender Database column gender SqlType(varchar), Length(8,true), Default(None)
   *  @param birthday Database column birthday SqlType(date), Default(None)
   *  @param specialty Database column specialty SqlType(varchar), Length(512,true), Default(None)
   *  @param nationality Database column nationality SqlType(varchar), Length(8,true), Default(None)
   *  @param isFundManager Database column is_fund_manager SqlType(bool), Default(Some(true))
   *  @param isQualificationAcquired Database column is_qualification_acquired SqlType(bool), Default(None)
   *  @param education Database column education SqlType(varchar), Length(512,true), Default(None)
   *  @param jobTitle Database column job_title SqlType(varchar), Length(128,true), Default(None)
   *  @param registrationNumber Database column registration_number SqlType(varchar), Length(32,true), Default(None)
   *  @param photo Database column photo SqlType(varchar), Length(128,true), Default(None)
   *  @param description Database column description SqlType(text), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class FundExecutivesRow(uid: String, employerUuid: String, name: String, status: Option[String] = None, workingYears: Option[Int] = None, workingExperience: Option[String] = None, gender: Option[String] = None, birthday: Option[java.sql.Date] = None, specialty: Option[String] = None, nationality: Option[String] = None, isFundManager: Option[Boolean] = Some(true), isQualificationAcquired: Option[Boolean] = None, education: Option[String] = None, jobTitle: Option[String] = None, registrationNumber: Option[String] = None, photo: Option[String] = None, description: Option[String] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching FundExecutivesRow objects using plain SQL queries */
  implicit def GetResultFundExecutivesRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Date]], e4: GR[Option[Boolean]], e5: GR[Boolean], e6: GR[java.sql.Timestamp]): GR[FundExecutivesRow] = GR{
    prs => import prs._
    FundExecutivesRow.tupled((<<[String], <<[String], <<[String], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[java.sql.Date], <<?[String], <<?[String], <<?[Boolean], <<?[Boolean], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table fund_executives. Objects of this class serve as prototypes for rows in queries. */
  class FundExecutives(_tableTag: Tag) extends Table[FundExecutivesRow](_tableTag, "fund_executives") {
    def * = (uid, employerUuid, name, status, workingYears, workingExperience, gender, birthday, specialty, nationality, isFundManager, isQualificationAcquired, education, jobTitle, registrationNumber, photo, description, editedBy, isDeleted, createdAt, updatedAt) <> (FundExecutivesRow.tupled, FundExecutivesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(employerUuid), Rep.Some(name), status, workingYears, workingExperience, gender, birthday, specialty, nationality, isFundManager, isQualificationAcquired, education, jobTitle, registrationNumber, photo, description, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> FundExecutivesRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18.get, _19.get, _20.get, _21.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column employer_uuid SqlType(varchar), Length(48,true) */
    val employerUuid: Rep[String] = column[String]("employer_uuid", O.Length(48,varying=true))
    /** Database column name SqlType(varchar), Length(16,true) */
    val name: Rep[String] = column[String]("name", O.Length(16,varying=true))
    /** Database column status SqlType(varchar), Length(64,true), Default(None) */
    val status: Rep[Option[String]] = column[Option[String]]("status", O.Length(64,varying=true), O.Default(None))
    /** Database column working_years SqlType(int4), Default(None) */
    val workingYears: Rep[Option[Int]] = column[Option[Int]]("working_years", O.Default(None))
    /** Database column working_experience SqlType(hstore), Length(2147483647,false), Default(None) */
    val workingExperience: Rep[Option[String]] = column[Option[String]]("working_experience", O.Length(2147483647,varying=false), O.Default(None))
    /** Database column gender SqlType(varchar), Length(8,true), Default(None) */
    val gender: Rep[Option[String]] = column[Option[String]]("gender", O.Length(8,varying=true), O.Default(None))
    /** Database column birthday SqlType(date), Default(None) */
    val birthday: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("birthday", O.Default(None))
    /** Database column specialty SqlType(varchar), Length(512,true), Default(None) */
    val specialty: Rep[Option[String]] = column[Option[String]]("specialty", O.Length(512,varying=true), O.Default(None))
    /** Database column nationality SqlType(varchar), Length(8,true), Default(None) */
    val nationality: Rep[Option[String]] = column[Option[String]]("nationality", O.Length(8,varying=true), O.Default(None))
    /** Database column is_fund_manager SqlType(bool), Default(Some(true)) */
    val isFundManager: Rep[Option[Boolean]] = column[Option[Boolean]]("is_fund_manager", O.Default(Some(true)))
    /** Database column is_qualification_acquired SqlType(bool), Default(None) */
    val isQualificationAcquired: Rep[Option[Boolean]] = column[Option[Boolean]]("is_qualification_acquired", O.Default(None))
    /** Database column education SqlType(varchar), Length(512,true), Default(None) */
    val education: Rep[Option[String]] = column[Option[String]]("education", O.Length(512,varying=true), O.Default(None))
    /** Database column job_title SqlType(varchar), Length(128,true), Default(None) */
    val jobTitle: Rep[Option[String]] = column[Option[String]]("job_title", O.Length(128,varying=true), O.Default(None))
    /** Database column registration_number SqlType(varchar), Length(32,true), Default(None) */
    val registrationNumber: Rep[Option[String]] = column[Option[String]]("registration_number", O.Length(32,varying=true), O.Default(None))
    /** Database column photo SqlType(varchar), Length(128,true), Default(None) */
    val photo: Rep[Option[String]] = column[Option[String]]("photo", O.Length(128,varying=true), O.Default(None))
    /** Database column description SqlType(text), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Foreign key referencing LegalEntity (database name fund_executives_employer_uuid_fkey) */
    lazy val legalEntityFk = foreignKey("fund_executives_employer_uuid_fkey", employerUuid, LegalEntity)(r => r.uid, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    /** Index over (createdAt) (database name idx_fund_executives_created_at) */
    val index1 = index("idx_fund_executives_created_at", createdAt)
    /** Index over (name) (database name idx_fund_executives_name) */
    val index2 = index("idx_fund_executives_name", name)
    /** Index over (updatedAt) (database name idx_fund_executives_updated_at) */
    val index3 = index("idx_fund_executives_updated_at", updatedAt)
  }
  /** Collection-like TableQuery object for table FundExecutives */
  lazy val FundExecutives = new TableQuery(tag => new FundExecutives(tag))

  /** Entity class storing rows of table FundManager
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param name Database column name SqlType(varchar)
   *  @param description Database column description SqlType(varchar)
   *  @param employerUuid Database column employer_uuid SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class FundManagerRow(uid: String, name: String, description: String, employerUuid: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching FundManagerRow objects using plain SQL queries */
  implicit def GetResultFundManagerRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[FundManagerRow] = GR{
    prs => import prs._
    FundManagerRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table fund_manager. Objects of this class serve as prototypes for rows in queries. */
  class FundManager(_tableTag: Tag) extends Table[FundManagerRow](_tableTag, "fund_manager") {
    def * = (uid, name, description, employerUuid, createdAt, updatedAt) <> (FundManagerRow.tupled, FundManagerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(name), Rep.Some(description), Rep.Some(employerUuid), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> FundManagerRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column name SqlType(varchar) */
    val name: Rep[String] = column[String]("name")
    /** Database column description SqlType(varchar) */
    val description: Rep[String] = column[String]("description")
    /** Database column employer_uuid SqlType(varchar) */
    val employerUuid: Rep[String] = column[String]("employer_uuid")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name fund_manager_created_at_idx) */
    val index1 = index("fund_manager_created_at_idx", createdAt)
    /** Index over (name) (database name fund_manager_name_idx) */
    val index2 = index("fund_manager_name_idx", name)
    /** Index over (updatedAt) (database name fund_manager_updated_at_idx) */
    val index3 = index("fund_manager_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table FundManager */
  lazy val FundManager = new TableQuery(tag => new FundManager(tag))

  /** Entity class storing rows of table HedgefundPerforamnce
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param productCode Database column product_code SqlType(varchar)
   *  @param validTimeEpoch Database column valid_time_epoch SqlType(int8range), Length(2147483647,false)
   *  @param currentPrice Database column current_price SqlType(float8)
   *  @param acrruedPrice Database column acrrued_price SqlType(float8)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class HedgefundPerforamnceRow(uid: String, productCode: String, validTimeEpoch: String, currentPrice: Double, acrruedPrice: Double, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HedgefundPerforamnceRow objects using plain SQL queries */
  implicit def GetResultHedgefundPerforamnceRow(implicit e0: GR[String], e1: GR[Double], e2: GR[java.sql.Timestamp]): GR[HedgefundPerforamnceRow] = GR{
    prs => import prs._
    HedgefundPerforamnceRow.tupled((<<[String], <<[String], <<[String], <<[Double], <<[Double], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hedgefund_perforamnce. Objects of this class serve as prototypes for rows in queries. */
  class HedgefundPerforamnce(_tableTag: Tag) extends Table[HedgefundPerforamnceRow](_tableTag, "hedgefund_perforamnce") {
    def * = (uid, productCode, validTimeEpoch, currentPrice, acrruedPrice, createdAt, updatedAt) <> (HedgefundPerforamnceRow.tupled, HedgefundPerforamnceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(productCode), Rep.Some(validTimeEpoch), Rep.Some(currentPrice), Rep.Some(acrruedPrice), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HedgefundPerforamnceRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column product_code SqlType(varchar) */
    val productCode: Rep[String] = column[String]("product_code")
    /** Database column valid_time_epoch SqlType(int8range), Length(2147483647,false) */
    val validTimeEpoch: Rep[String] = column[String]("valid_time_epoch", O.Length(2147483647,varying=false))
    /** Database column current_price SqlType(float8) */
    val currentPrice: Rep[Double] = column[Double]("current_price")
    /** Database column acrrued_price SqlType(float8) */
    val acrruedPrice: Rep[Double] = column[Double]("acrrued_price")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name hedgefund_perforamnce_created_at_idx) */
    val index1 = index("hedgefund_perforamnce_created_at_idx", createdAt)
    /** Uniqueness Index over (productCode) (database name hedgefund_perforamnce_product_code_idx) */
    val index2 = index("hedgefund_perforamnce_product_code_idx", productCode, unique=true)
    /** Index over (updatedAt) (database name hedgefund_perforamnce_updated_at_idx) */
    val index3 = index("hedgefund_perforamnce_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table HedgefundPerforamnce */
  lazy val HedgefundPerforamnce = new TableQuery(tag => new HedgefundPerforamnce(tag))

  /** Entity class storing rows of table HedgeFundProductFee
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param productUid Database column product_uid SqlType(varchar), Length(48,true)
   *  @param managementFee Database column management_fee SqlType(float8), Default(None)
   *  @param trusteeFee Database column trustee_fee SqlType(float8), Default(None)
   *  @param advisoryFee Database column advisory_fee SqlType(float8), Default(None)
   *  @param subscriptionFee Database column subscription_fee SqlType(float8), Default(None)
   *  @param purchaseFee Database column purchase_fee SqlType(float8), Default(None)
   *  @param redeemFee Database column redeem_fee SqlType(float8), Default(None)
   *  @param channelFee Database column channel_fee SqlType(float8), Default(None)
   *  @param serviceFee Database column service_fee SqlType(float8), Default(None)
   *  @param othersFee Database column others_fee SqlType(float8), Default(None)
   *  @param commission Database column commission SqlType(float8), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamp)
   *  @param updatedAt Database column updated_at SqlType(timestamp) */
  case class HedgeFundProductFeeRow(uid: String, productUid: String, managementFee: Option[Double] = None, trusteeFee: Option[Double] = None, advisoryFee: Option[Double] = None, subscriptionFee: Option[Double] = None, purchaseFee: Option[Double] = None, redeemFee: Option[Double] = None, channelFee: Option[Double] = None, serviceFee: Option[Double] = None, othersFee: Option[Double] = None, commission: Option[Double] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HedgeFundProductFeeRow objects using plain SQL queries */
  implicit def GetResultHedgeFundProductFeeRow(implicit e0: GR[String], e1: GR[Option[Double]], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[HedgeFundProductFeeRow] = GR{
    prs => import prs._
    HedgeFundProductFeeRow.tupled((<<[String], <<[String], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<?[Double], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hedge_fund_product_fee. Objects of this class serve as prototypes for rows in queries. */
  class HedgeFundProductFee(_tableTag: Tag) extends Table[HedgeFundProductFeeRow](_tableTag, "hedge_fund_product_fee") {
    def * = (uid, productUid, managementFee, trusteeFee, advisoryFee, subscriptionFee, purchaseFee, redeemFee, channelFee, serviceFee, othersFee, commission, editedBy, isDeleted, createdAt, updatedAt) <> (HedgeFundProductFeeRow.tupled, HedgeFundProductFeeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(productUid), managementFee, trusteeFee, advisoryFee, subscriptionFee, purchaseFee, redeemFee, channelFee, serviceFee, othersFee, commission, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HedgeFundProductFeeRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13.get, _14.get, _15.get, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column product_uid SqlType(varchar), Length(48,true) */
    val productUid: Rep[String] = column[String]("product_uid", O.Length(48,varying=true))
    /** Database column management_fee SqlType(float8), Default(None) */
    val managementFee: Rep[Option[Double]] = column[Option[Double]]("management_fee", O.Default(None))
    /** Database column trustee_fee SqlType(float8), Default(None) */
    val trusteeFee: Rep[Option[Double]] = column[Option[Double]]("trustee_fee", O.Default(None))
    /** Database column advisory_fee SqlType(float8), Default(None) */
    val advisoryFee: Rep[Option[Double]] = column[Option[Double]]("advisory_fee", O.Default(None))
    /** Database column subscription_fee SqlType(float8), Default(None) */
    val subscriptionFee: Rep[Option[Double]] = column[Option[Double]]("subscription_fee", O.Default(None))
    /** Database column purchase_fee SqlType(float8), Default(None) */
    val purchaseFee: Rep[Option[Double]] = column[Option[Double]]("purchase_fee", O.Default(None))
    /** Database column redeem_fee SqlType(float8), Default(None) */
    val redeemFee: Rep[Option[Double]] = column[Option[Double]]("redeem_fee", O.Default(None))
    /** Database column channel_fee SqlType(float8), Default(None) */
    val channelFee: Rep[Option[Double]] = column[Option[Double]]("channel_fee", O.Default(None))
    /** Database column service_fee SqlType(float8), Default(None) */
    val serviceFee: Rep[Option[Double]] = column[Option[Double]]("service_fee", O.Default(None))
    /** Database column others_fee SqlType(float8), Default(None) */
    val othersFee: Rep[Option[Double]] = column[Option[Double]]("others_fee", O.Default(None))
    /** Database column commission SqlType(float8), Default(None) */
    val commission: Rep[Option[Double]] = column[Option[Double]]("commission", O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamp) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name hedge_fund_product_fee_created_at_idx) */
    val index1 = index("hedge_fund_product_fee_created_at_idx", createdAt)
    /** Index over (productUid) (database name hedge_fund_product_fee_product_uid_idx) */
    val index2 = index("hedge_fund_product_fee_product_uid_idx", productUid)
    /** Index over (updatedAt) (database name hedge_fund_product_fee_updated_at_idx) */
    val index3 = index("hedge_fund_product_fee_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table HedgeFundProductFee */
  lazy val HedgeFundProductFee = new TableQuery(tag => new HedgeFundProductFee(tag))

  /** Entity class storing rows of table HedgeFundProductInfo
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param strategyUid Database column strategy_uid SqlType(varchar), Length(48,true)
   *  @param teamUid Database column team_uid SqlType(varchar), Length(48,true)
   *  @param ownerUid Database column owner_uid SqlType(varchar), Length(48,true)
   *  @param productName Database column product_name SqlType(varchar), Length(36,true)
   *  @param productStatus Database column product_status SqlType(varchar), Length(36,true), Default(None)
   *  @param benchmark Database column benchmark SqlType(varchar), Length(16,true), Default(None)
   *  @param ifOperated Database column if_operated SqlType(bool), Default(None)
   *  @param foundedDate Database column founded_date SqlType(date), Default(None)
   *  @param terminatedDate Database column terminated_date SqlType(date), Default(None)
   *  @param ifInvested Database column if_invested SqlType(bool), Default(None)
   *  @param dataType Database column data_type SqlType(int4), Default(None)
   *  @param dataFrequency Database column data_frequency SqlType(int4), Default(None)
   *  @param fundStatus Database column fund_status SqlType(int4), Default(None)
   *  @param size Database column size SqlType(float8), Default(None)
   *  @param duration Database column duration SqlType(varchar), Length(16,true), Default(None)
   *  @param cooperationIntention Database column cooperation_intention SqlType(varchar), Length(64,true), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class HedgeFundProductInfoRow(uid: String, strategyUid: String, teamUid: String, ownerUid: String, productName: String, productStatus: Option[String] = None, benchmark: Option[String] = None, ifOperated: Option[Boolean] = None, foundedDate: Option[java.sql.Date] = None, terminatedDate: Option[java.sql.Date] = None, ifInvested: Option[Boolean] = None, dataType: Option[Int] = None, dataFrequency: Option[Int] = None, fundStatus: Option[Int] = None, size: Option[Double] = None, duration: Option[String] = None, cooperationIntention: Option[String] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HedgeFundProductInfoRow objects using plain SQL queries */
  implicit def GetResultHedgeFundProductInfoRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Option[Boolean]], e3: GR[Option[java.sql.Date]], e4: GR[Option[Int]], e5: GR[Option[Double]], e6: GR[Boolean], e7: GR[java.sql.Timestamp]): GR[HedgeFundProductInfoRow] = GR{
    prs => import prs._
    HedgeFundProductInfoRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<?[String], <<?[String], <<?[Boolean], <<?[java.sql.Date], <<?[java.sql.Date], <<?[Boolean], <<?[Int], <<?[Int], <<?[Int], <<?[Double], <<?[String], <<?[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hedge_fund_product_info. Objects of this class serve as prototypes for rows in queries. */
  class HedgeFundProductInfo(_tableTag: Tag) extends Table[HedgeFundProductInfoRow](_tableTag, "hedge_fund_product_info") {
    def * = (uid, strategyUid, teamUid, ownerUid, productName, productStatus, benchmark, ifOperated, foundedDate, terminatedDate, ifInvested, dataType, dataFrequency, fundStatus, size, duration, cooperationIntention, editedBy, isDeleted, createdAt, updatedAt) <> (HedgeFundProductInfoRow.tupled, HedgeFundProductInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(strategyUid), Rep.Some(teamUid), Rep.Some(ownerUid), Rep.Some(productName), productStatus, benchmark, ifOperated, foundedDate, terminatedDate, ifInvested, dataType, dataFrequency, fundStatus, size, duration, cooperationIntention, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HedgeFundProductInfoRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18.get, _19.get, _20.get, _21.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column strategy_uid SqlType(varchar), Length(48,true) */
    val strategyUid: Rep[String] = column[String]("strategy_uid", O.Length(48,varying=true))
    /** Database column team_uid SqlType(varchar), Length(48,true) */
    val teamUid: Rep[String] = column[String]("team_uid", O.Length(48,varying=true))
    /** Database column owner_uid SqlType(varchar), Length(48,true) */
    val ownerUid: Rep[String] = column[String]("owner_uid", O.Length(48,varying=true))
    /** Database column product_name SqlType(varchar), Length(36,true) */
    val productName: Rep[String] = column[String]("product_name", O.Length(36,varying=true))
    /** Database column product_status SqlType(varchar), Length(36,true), Default(None) */
    val productStatus: Rep[Option[String]] = column[Option[String]]("product_status", O.Length(36,varying=true), O.Default(None))
    /** Database column benchmark SqlType(varchar), Length(16,true), Default(None) */
    val benchmark: Rep[Option[String]] = column[Option[String]]("benchmark", O.Length(16,varying=true), O.Default(None))
    /** Database column if_operated SqlType(bool), Default(None) */
    val ifOperated: Rep[Option[Boolean]] = column[Option[Boolean]]("if_operated", O.Default(None))
    /** Database column founded_date SqlType(date), Default(None) */
    val foundedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("founded_date", O.Default(None))
    /** Database column terminated_date SqlType(date), Default(None) */
    val terminatedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("terminated_date", O.Default(None))
    /** Database column if_invested SqlType(bool), Default(None) */
    val ifInvested: Rep[Option[Boolean]] = column[Option[Boolean]]("if_invested", O.Default(None))
    /** Database column data_type SqlType(int4), Default(None) */
    val dataType: Rep[Option[Int]] = column[Option[Int]]("data_type", O.Default(None))
    /** Database column data_frequency SqlType(int4), Default(None) */
    val dataFrequency: Rep[Option[Int]] = column[Option[Int]]("data_frequency", O.Default(None))
    /** Database column fund_status SqlType(int4), Default(None) */
    val fundStatus: Rep[Option[Int]] = column[Option[Int]]("fund_status", O.Default(None))
    /** Database column size SqlType(float8), Default(None) */
    val size: Rep[Option[Double]] = column[Option[Double]]("size", O.Default(None))
    /** Database column duration SqlType(varchar), Length(16,true), Default(None) */
    val duration: Rep[Option[String]] = column[Option[String]]("duration", O.Length(16,varying=true), O.Default(None))
    /** Database column cooperation_intention SqlType(varchar), Length(64,true), Default(None) */
    val cooperationIntention: Rep[Option[String]] = column[Option[String]]("cooperation_intention", O.Length(64,varying=true), O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Foreign key referencing HedgeFundStrategyInfo (database name hedge_fund_product_info_strategy_uid_fkey) */
    lazy val hedgeFundStrategyInfoFk = foreignKey("hedge_fund_product_info_strategy_uid_fkey", strategyUid, HedgeFundStrategyInfo)(r => r.uid, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    /** Index over (createdAt) (database name idx_hedge_fund_product_info_created_at) */
    val index1 = index("idx_hedge_fund_product_info_created_at", createdAt)
    /** Index over (ownerUid) (database name idx_hedge_fund_product_info_owner) */
    val index2 = index("idx_hedge_fund_product_info_owner", ownerUid)
    /** Index over (productName) (database name idx_hedge_fund_product_info_product_name) */
    val index3 = index("idx_hedge_fund_product_info_product_name", productName)
    /** Index over (teamUid) (database name idx_hedge_fund_product_info_team) */
    val index4 = index("idx_hedge_fund_product_info_team", teamUid)
    /** Index over (updatedAt) (database name idx_hedge_fund_product_info_updated_at) */
    val index5 = index("idx_hedge_fund_product_info_updated_at", updatedAt)
  }
  /** Collection-like TableQuery object for table HedgeFundProductInfo */
  lazy val HedgeFundProductInfo = new TableQuery(tag => new HedgeFundProductInfo(tag))

  /** Entity class storing rows of table HedgeFundProductNetValue
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param productUid Database column product_uid SqlType(varchar)
   *  @param date Database column date SqlType(date)
   *  @param currentPrice Database column current_price SqlType(float8)
   *  @param accumulatedPrice Database column accumulated_price SqlType(float8)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamp)
   *  @param updatedAt Database column updated_at SqlType(timestamp) */
  case class HedgeFundProductNetValueRow(uid: String, productUid: String, date: java.sql.Date, currentPrice: Double, accumulatedPrice: Double, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HedgeFundProductNetValueRow objects using plain SQL queries */
  implicit def GetResultHedgeFundProductNetValueRow(implicit e0: GR[String], e1: GR[java.sql.Date], e2: GR[Double], e3: GR[Boolean], e4: GR[java.sql.Timestamp]): GR[HedgeFundProductNetValueRow] = GR{
    prs => import prs._
    HedgeFundProductNetValueRow.tupled((<<[String], <<[String], <<[java.sql.Date], <<[Double], <<[Double], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hedge_fund_product_net_value. Objects of this class serve as prototypes for rows in queries. */
  class HedgeFundProductNetValue(_tableTag: Tag) extends Table[HedgeFundProductNetValueRow](_tableTag, "hedge_fund_product_net_value") {
    def * = (uid, productUid, date, currentPrice, accumulatedPrice, editedBy, isDeleted, createdAt, updatedAt) <> (HedgeFundProductNetValueRow.tupled, HedgeFundProductNetValueRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(productUid), Rep.Some(date), Rep.Some(currentPrice), Rep.Some(accumulatedPrice), Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HedgeFundProductNetValueRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column product_uid SqlType(varchar) */
    val productUid: Rep[String] = column[String]("product_uid")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
    /** Database column current_price SqlType(float8) */
    val currentPrice: Rep[Double] = column[Double]("current_price")
    /** Database column accumulated_price SqlType(float8) */
    val accumulatedPrice: Rep[Double] = column[Double]("accumulated_price")
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamp) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamp) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name hedge_fund_product_net_value_created_at_idx) */
    val index1 = index("hedge_fund_product_net_value_created_at_idx", createdAt)
    /** Uniqueness Index over (productUid,date) (database name hedge_fund_product_net_value_product_uid_date_key) */
    val index2 = index("hedge_fund_product_net_value_product_uid_date_key", (productUid, date), unique=true)
    /** Index over (productUid) (database name hedge_fund_product_net_value_product_uid_idx) */
    val index3 = index("hedge_fund_product_net_value_product_uid_idx", productUid)
    /** Index over (updatedAt) (database name hedge_fund_product_net_value_updated_at_idx) */
    val index4 = index("hedge_fund_product_net_value_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table HedgeFundProductNetValue */
  lazy val HedgeFundProductNetValue = new TableQuery(tag => new HedgeFundProductNetValue(tag))

  /** Entity class storing rows of table HedgeFundStrategyInfo
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param name Database column name SqlType(varchar), Length(48,true)
   *  @param manager Database column manager SqlType(varchar), Length(32,true)
   *  @param custodian Database column custodian SqlType(varchar), Length(48,true), Default(None)
   *  @param trustee Database column trustee SqlType(varchar), Length(48,true), Default(None)
   *  @param counselor Database column counselor SqlType(varchar), Length(48,true), Default(None)
   *  @param investedAssetType Database column invested_asset_type SqlType(varchar), Length(128,true), Default(None)
   *  @param strategyType Database column strategy_type SqlType(varchar), Length(64,true), Default(None)
   *  @param purchaseRedeem Database column purchase_redeem SqlType(varchar), Length(512,true), Default(None)
   *  @param complianceManagementIntroduction Database column compliance_management_introduction SqlType(varchar), Length(512,true), Default(None)
   *  @param riskManagementIntroduction Database column risk_management_introduction SqlType(varchar), Length(512,true), Default(None)
   *  @param warningLine Database column warning_line SqlType(varchar), Length(256,true), Default(None)
   *  @param liquidationLine Database column liquidation_line SqlType(varchar), Length(256,true), Default(None)
   *  @param otherIntroduction Database column other_introduction SqlType(text), Default(None)
   *  @param assetAllocation Database column asset_allocation SqlType(varchar), Length(512,true), Default(None)
   *  @param structure Database column structure SqlType(varchar), Length(128,true), Default(None)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class HedgeFundStrategyInfoRow(uid: String, name: String, manager: String, custodian: Option[String] = None, trustee: Option[String] = None, counselor: Option[String] = None, investedAssetType: Option[String] = None, strategyType: Option[String] = None, purchaseRedeem: Option[String] = None, complianceManagementIntroduction: Option[String] = None, riskManagementIntroduction: Option[String] = None, warningLine: Option[String] = None, liquidationLine: Option[String] = None, otherIntroduction: Option[String] = None, assetAllocation: Option[String] = None, structure: Option[String] = None, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HedgeFundStrategyInfoRow objects using plain SQL queries */
  implicit def GetResultHedgeFundStrategyInfoRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[HedgeFundStrategyInfoRow] = GR{
    prs => import prs._
    HedgeFundStrategyInfoRow.tupled((<<[String], <<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hedge_fund_strategy_info. Objects of this class serve as prototypes for rows in queries. */
  class HedgeFundStrategyInfo(_tableTag: Tag) extends Table[HedgeFundStrategyInfoRow](_tableTag, "hedge_fund_strategy_info") {
    def * = (uid, name, manager, custodian, trustee, counselor, investedAssetType, strategyType, purchaseRedeem, complianceManagementIntroduction, riskManagementIntroduction, warningLine, liquidationLine, otherIntroduction, assetAllocation, structure, editedBy, isDeleted, createdAt, updatedAt) <> (HedgeFundStrategyInfoRow.tupled, HedgeFundStrategyInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(name), Rep.Some(manager), custodian, trustee, counselor, investedAssetType, strategyType, purchaseRedeem, complianceManagementIntroduction, riskManagementIntroduction, warningLine, liquidationLine, otherIntroduction, assetAllocation, structure, Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HedgeFundStrategyInfoRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17.get, _18.get, _19.get, _20.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column name SqlType(varchar), Length(48,true) */
    val name: Rep[String] = column[String]("name", O.Length(48,varying=true))
    /** Database column manager SqlType(varchar), Length(32,true) */
    val manager: Rep[String] = column[String]("manager", O.Length(32,varying=true))
    /** Database column custodian SqlType(varchar), Length(48,true), Default(None) */
    val custodian: Rep[Option[String]] = column[Option[String]]("custodian", O.Length(48,varying=true), O.Default(None))
    /** Database column trustee SqlType(varchar), Length(48,true), Default(None) */
    val trustee: Rep[Option[String]] = column[Option[String]]("trustee", O.Length(48,varying=true), O.Default(None))
    /** Database column counselor SqlType(varchar), Length(48,true), Default(None) */
    val counselor: Rep[Option[String]] = column[Option[String]]("counselor", O.Length(48,varying=true), O.Default(None))
    /** Database column invested_asset_type SqlType(varchar), Length(128,true), Default(None) */
    val investedAssetType: Rep[Option[String]] = column[Option[String]]("invested_asset_type", O.Length(128,varying=true), O.Default(None))
    /** Database column strategy_type SqlType(varchar), Length(64,true), Default(None) */
    val strategyType: Rep[Option[String]] = column[Option[String]]("strategy_type", O.Length(64,varying=true), O.Default(None))
    /** Database column purchase_redeem SqlType(varchar), Length(512,true), Default(None) */
    val purchaseRedeem: Rep[Option[String]] = column[Option[String]]("purchase_redeem", O.Length(512,varying=true), O.Default(None))
    /** Database column compliance_management_introduction SqlType(varchar), Length(512,true), Default(None) */
    val complianceManagementIntroduction: Rep[Option[String]] = column[Option[String]]("compliance_management_introduction", O.Length(512,varying=true), O.Default(None))
    /** Database column risk_management_introduction SqlType(varchar), Length(512,true), Default(None) */
    val riskManagementIntroduction: Rep[Option[String]] = column[Option[String]]("risk_management_introduction", O.Length(512,varying=true), O.Default(None))
    /** Database column warning_line SqlType(varchar), Length(256,true), Default(None) */
    val warningLine: Rep[Option[String]] = column[Option[String]]("warning_line", O.Length(256,varying=true), O.Default(None))
    /** Database column liquidation_line SqlType(varchar), Length(256,true), Default(None) */
    val liquidationLine: Rep[Option[String]] = column[Option[String]]("liquidation_line", O.Length(256,varying=true), O.Default(None))
    /** Database column other_introduction SqlType(text), Default(None) */
    val otherIntroduction: Rep[Option[String]] = column[Option[String]]("other_introduction", O.Default(None))
    /** Database column asset_allocation SqlType(varchar), Length(512,true), Default(None) */
    val assetAllocation: Rep[Option[String]] = column[Option[String]]("asset_allocation", O.Length(512,varying=true), O.Default(None))
    /** Database column structure SqlType(varchar), Length(128,true), Default(None) */
    val structure: Rep[Option[String]] = column[Option[String]]("structure", O.Length(128,varying=true), O.Default(None))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Uniqueness Index over (name,manager) (database name hedge_fund_strategy_info_name_manager_key) */
    val index1 = index("hedge_fund_strategy_info_name_manager_key", (name, manager), unique=true)
    /** Index over (createdAt) (database name idx_hedge_fund_strategy_info_created_at) */
    val index2 = index("idx_hedge_fund_strategy_info_created_at", createdAt)
    /** Index over (updatedAt) (database name idx_hedge_fund_strategy_info_updated_at) */
    val index3 = index("idx_hedge_fund_strategy_info_updated_at", updatedAt)
  }
  /** Collection-like TableQuery object for table HedgeFundStrategyInfo */
  lazy val HedgeFundStrategyInfo = new TableQuery(tag => new HedgeFundStrategyInfo(tag))

  /** Row type of table HlistTestTable */
  type HlistTestTableRow = HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[Option[String],HCons[Option[String],HCons[Option[Boolean],HCons[Option[java.sql.Date],HCons[Option[java.sql.Date],HCons[Option[Boolean],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Double],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[java.sql.Timestamp,HCons[java.sql.Timestamp,HNil]]]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for HlistTestTableRow providing default values if available in the database schema. */
  def HlistTestTableRow(uid: String, strategyUid: String, teamUid: String, ownerUid: String, productName: String, productStatus: Option[String] = None, benchmark: Option[String] = None, ifOperated: Option[Boolean] = None, establishedDate: Option[java.sql.Date] = None, terminatedDate: Option[java.sql.Date] = None, ifInvested: Option[Boolean] = None, dataType: Option[Int] = None, dataFrequency: Option[Int] = None, fundStatus: Option[Int] = None, size: Option[Double] = None, duration: Option[String] = None, cooperationIntention: Option[String] = None, column1: Option[String] = None, column2: Option[String] = None, column3: Option[String] = None, column4: Option[String] = None, column5: Option[String] = None, column6: Option[String] = None, column7: Option[String] = None, column8: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp): HlistTestTableRow = {
    uid :: strategyUid :: teamUid :: ownerUid :: productName :: productStatus :: benchmark :: ifOperated :: establishedDate :: terminatedDate :: ifInvested :: dataType :: dataFrequency :: fundStatus :: size :: duration :: cooperationIntention :: column1 :: column2 :: column3 :: column4 :: column5 :: column6 :: column7 :: column8 :: createdAt :: updatedAt :: HNil
  }
  /** GetResult implicit for fetching HlistTestTableRow objects using plain SQL queries */
  implicit def GetResultHlistTestTableRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Option[Boolean]], e3: GR[Option[java.sql.Date]], e4: GR[Option[Int]], e5: GR[Option[Double]], e6: GR[java.sql.Timestamp]): GR[HlistTestTableRow] = GR{
    prs => import prs._
    <<[String] :: <<[String] :: <<[String] :: <<[String] :: <<[String] :: <<?[String] :: <<?[String] :: <<?[Boolean] :: <<?[java.sql.Date] :: <<?[java.sql.Date] :: <<?[Boolean] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Double] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<[java.sql.Timestamp] :: <<[java.sql.Timestamp] :: HNil
  }
  /** Table description of table hlist_test_table. Objects of this class serve as prototypes for rows in queries. */
  class HlistTestTable(_tableTag: Tag) extends Table[HlistTestTableRow](_tableTag, "hlist_test_table") {
    def * = uid :: strategyUid :: teamUid :: ownerUid :: productName :: productStatus :: benchmark :: ifOperated :: establishedDate :: terminatedDate :: ifInvested :: dataType :: dataFrequency :: fundStatus :: size :: duration :: cooperationIntention :: column1 :: column2 :: column3 :: column4 :: column5 :: column6 :: column7 :: column8 :: createdAt :: updatedAt :: HNil

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column strategy_uid SqlType(varchar) */
    val strategyUid: Rep[String] = column[String]("strategy_uid")
    /** Database column team_uid SqlType(varchar) */
    val teamUid: Rep[String] = column[String]("team_uid")
    /** Database column owner_uid SqlType(varchar) */
    val ownerUid: Rep[String] = column[String]("owner_uid")
    /** Database column product_name SqlType(varchar) */
    val productName: Rep[String] = column[String]("product_name")
    /** Database column product_status SqlType(varchar), Default(None) */
    val productStatus: Rep[Option[String]] = column[Option[String]]("product_status", O.Default(None))
    /** Database column benchmark SqlType(varchar), Default(None) */
    val benchmark: Rep[Option[String]] = column[Option[String]]("benchmark", O.Default(None))
    /** Database column if_operated SqlType(bool), Default(None) */
    val ifOperated: Rep[Option[Boolean]] = column[Option[Boolean]]("if_operated", O.Default(None))
    /** Database column established_date SqlType(date), Default(None) */
    val establishedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("established_date", O.Default(None))
    /** Database column terminated_date SqlType(date), Default(None) */
    val terminatedDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("terminated_date", O.Default(None))
    /** Database column if_invested SqlType(bool), Default(None) */
    val ifInvested: Rep[Option[Boolean]] = column[Option[Boolean]]("if_invested", O.Default(None))
    /** Database column data_type SqlType(int4), Default(None) */
    val dataType: Rep[Option[Int]] = column[Option[Int]]("data_type", O.Default(None))
    /** Database column data_frequency SqlType(int4), Default(None) */
    val dataFrequency: Rep[Option[Int]] = column[Option[Int]]("data_frequency", O.Default(None))
    /** Database column fund_status SqlType(int4), Default(None) */
    val fundStatus: Rep[Option[Int]] = column[Option[Int]]("fund_status", O.Default(None))
    /** Database column size SqlType(float8), Default(None) */
    val size: Rep[Option[Double]] = column[Option[Double]]("size", O.Default(None))
    /** Database column duration SqlType(varchar), Default(None) */
    val duration: Rep[Option[String]] = column[Option[String]]("duration", O.Default(None))
    /** Database column cooperation_intention SqlType(varchar), Default(None) */
    val cooperationIntention: Rep[Option[String]] = column[Option[String]]("cooperation_intention", O.Default(None))
    /** Database column column_1 SqlType(varchar), Default(None) */
    val column1: Rep[Option[String]] = column[Option[String]]("column_1", O.Default(None))
    /** Database column column_2 SqlType(varchar), Default(None) */
    val column2: Rep[Option[String]] = column[Option[String]]("column_2", O.Default(None))
    /** Database column column_3 SqlType(varchar), Default(None) */
    val column3: Rep[Option[String]] = column[Option[String]]("column_3", O.Default(None))
    /** Database column column_4 SqlType(varchar), Default(None) */
    val column4: Rep[Option[String]] = column[Option[String]]("column_4", O.Default(None))
    /** Database column column_5 SqlType(varchar), Default(None) */
    val column5: Rep[Option[String]] = column[Option[String]]("column_5", O.Default(None))
    /** Database column column_6 SqlType(varchar), Default(None) */
    val column6: Rep[Option[String]] = column[Option[String]]("column_6", O.Default(None))
    /** Database column column_7 SqlType(varchar), Default(None) */
    val column7: Rep[Option[String]] = column[Option[String]]("column_7", O.Default(None))
    /** Database column column_8 SqlType(varchar), Default(None) */
    val column8: Rep[Option[String]] = column[Option[String]]("column_8", O.Default(None))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table HlistTestTable */
  lazy val HlistTestTable = new TableQuery(tag => new HlistTestTable(tag))

  /** Entity class storing rows of table HupoUser
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param nickName Database column nick_name SqlType(varchar)
   *  @param lastName Database column last_name SqlType(varchar)
   *  @param firstName Database column first_name SqlType(varchar)
   *  @param email Database column email SqlType(varchar)
   *  @param roleNames Database column role_names SqlType(_text), Length(2147483647,false)
   *  @param mobile Database column mobile SqlType(varchar)
   *  @param password Database column password SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class HupoUserRow(uid: String, nickName: String, lastName: String, firstName: String, email: String, roleNames: String, mobile: String, password: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HupoUserRow objects using plain SQL queries */
  implicit def GetResultHupoUserRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[HupoUserRow] = GR{
    prs => import prs._
    HupoUserRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hupo_user. Objects of this class serve as prototypes for rows in queries. */
  class HupoUser(_tableTag: Tag) extends Table[HupoUserRow](_tableTag, "hupo_user") {
    def * = (uid, nickName, lastName, firstName, email, roleNames, mobile, password, createdAt, updatedAt) <> (HupoUserRow.tupled, HupoUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(nickName), Rep.Some(lastName), Rep.Some(firstName), Rep.Some(email), Rep.Some(roleNames), Rep.Some(mobile), Rep.Some(password), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HupoUserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column nick_name SqlType(varchar) */
    val nickName: Rep[String] = column[String]("nick_name")
    /** Database column last_name SqlType(varchar) */
    val lastName: Rep[String] = column[String]("last_name")
    /** Database column first_name SqlType(varchar) */
    val firstName: Rep[String] = column[String]("first_name")
    /** Database column email SqlType(varchar) */
    val email: Rep[String] = column[String]("email")
    /** Database column role_names SqlType(_text), Length(2147483647,false) */
    val roleNames: Rep[String] = column[String]("role_names", O.Length(2147483647,varying=false))
    /** Database column mobile SqlType(varchar) */
    val mobile: Rep[String] = column[String]("mobile")
    /** Database column password SqlType(varchar) */
    val password: Rep[String] = column[String]("password")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name hupo_user_created_at_idx) */
    val index1 = index("hupo_user_created_at_idx", createdAt)
    /** Uniqueness Index over (email) (database name hupo_user_email_idx) */
    val index2 = index("hupo_user_email_idx", email, unique=true)
    /** Index over (firstName) (database name hupo_user_first_name_idx) */
    val index3 = index("hupo_user_first_name_idx", firstName)
    /** Index over (lastName) (database name hupo_user_last_name_idx) */
    val index4 = index("hupo_user_last_name_idx", lastName)
    /** Uniqueness Index over (mobile) (database name hupo_user_mobile_idx) */
    val index5 = index("hupo_user_mobile_idx", mobile, unique=true)
    /** Uniqueness Index over (nickName) (database name hupo_user_nick_name_idx) */
    val index6 = index("hupo_user_nick_name_idx", nickName, unique=true)
    /** Index over (updatedAt) (database name hupo_user_updated_at_idx) */
    val index7 = index("hupo_user_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table HupoUser */
  lazy val HupoUser = new TableQuery(tag => new HupoUser(tag))

  /** Entity class storing rows of table HupoUserRolePermission
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param role Database column role SqlType(varchar)
   *  @param path Database column path SqlType(varchar)
   *  @param operation Database column operation SqlType(varchar)
   *  @param permission Database column permission SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class HupoUserRolePermissionRow(uid: String, role: String, path: String, operation: String, permission: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching HupoUserRolePermissionRow objects using plain SQL queries */
  implicit def GetResultHupoUserRolePermissionRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[HupoUserRolePermissionRow] = GR{
    prs => import prs._
    HupoUserRolePermissionRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table hupo_user_role_permission. Objects of this class serve as prototypes for rows in queries. */
  class HupoUserRolePermission(_tableTag: Tag) extends Table[HupoUserRolePermissionRow](_tableTag, "hupo_user_role_permission") {
    def * = (uid, role, path, operation, permission, createdAt, updatedAt) <> (HupoUserRolePermissionRow.tupled, HupoUserRolePermissionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(role), Rep.Some(path), Rep.Some(operation), Rep.Some(permission), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> HupoUserRolePermissionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column role SqlType(varchar) */
    val role: Rep[String] = column[String]("role")
    /** Database column path SqlType(varchar) */
    val path: Rep[String] = column[String]("path")
    /** Database column operation SqlType(varchar) */
    val operation: Rep[String] = column[String]("operation")
    /** Database column permission SqlType(varchar) */
    val permission: Rep[String] = column[String]("permission")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name hupo_user_role_permission_created_at_idx) */
    val index1 = index("hupo_user_role_permission_created_at_idx", createdAt)
    /** Index over (operation,role) (database name hupo_user_role_permission_path_operation_role_idx) */
    val index2 = index("hupo_user_role_permission_path_operation_role_idx", (operation, role))
    /** Index over (updatedAt) (database name hupo_user_role_permission_updated_at_idx) */
    val index3 = index("hupo_user_role_permission_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table HupoUserRolePermission */
  lazy val HupoUserRolePermission = new TableQuery(tag => new HupoUserRolePermission(tag))

  /** Entity class storing rows of table InvestmentTeam
   *  @param uid Database column uid SqlType(varchar), PrimaryKey, Length(48,true)
   *  @param ownerUid Database column owner_uid SqlType(varchar), Length(48,true)
   *  @param memberUids Database column member_uids SqlType(_text), Length(2147483647,false)
   *  @param managerUid Database column manager_uid SqlType(varchar), Length(48,true), Default(None)
   *  @param legalEntityUid Database column legal_entity_uid SqlType(varchar), Length(48,true)
   *  @param isCooperated Database column is_cooperated SqlType(bool), Default(Some(false))
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true), Default(None)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class InvestmentTeamRow(uid: String, ownerUid: String, memberUids: String, managerUid: Option[String] = None, legalEntityUid: String, isCooperated: Option[Boolean] = Some(false), editedBy: Option[String] = None, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching InvestmentTeamRow objects using plain SQL queries */
  implicit def GetResultInvestmentTeamRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Option[Boolean]], e3: GR[Boolean], e4: GR[java.sql.Timestamp]): GR[InvestmentTeamRow] = GR{
    prs => import prs._
    InvestmentTeamRow.tupled((<<[String], <<[String], <<[String], <<?[String], <<[String], <<?[Boolean], <<?[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table investment_team. Objects of this class serve as prototypes for rows in queries. */
  class InvestmentTeam(_tableTag: Tag) extends Table[InvestmentTeamRow](_tableTag, "investment_team") {
    def * = (uid, ownerUid, memberUids, managerUid, legalEntityUid, isCooperated, editedBy, isDeleted, createdAt, updatedAt) <> (InvestmentTeamRow.tupled, InvestmentTeamRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(ownerUid), Rep.Some(memberUids), managerUid, Rep.Some(legalEntityUid), isCooperated, editedBy, Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> InvestmentTeamRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6, _7, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey, Length(48,true) */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey, O.Length(48,varying=true))
    /** Database column owner_uid SqlType(varchar), Length(48,true) */
    val ownerUid: Rep[String] = column[String]("owner_uid", O.Length(48,varying=true))
    /** Database column member_uids SqlType(_text), Length(2147483647,false) */
    val memberUids: Rep[String] = column[String]("member_uids", O.Length(2147483647,varying=false))
    /** Database column manager_uid SqlType(varchar), Length(48,true), Default(None) */
    val managerUid: Rep[Option[String]] = column[Option[String]]("manager_uid", O.Length(48,varying=true), O.Default(None))
    /** Database column legal_entity_uid SqlType(varchar), Length(48,true) */
    val legalEntityUid: Rep[String] = column[String]("legal_entity_uid", O.Length(48,varying=true))
    /** Database column is_cooperated SqlType(bool), Default(Some(false)) */
    val isCooperated: Rep[Option[Boolean]] = column[Option[Boolean]]("is_cooperated", O.Default(Some(false)))
    /** Database column edited_by SqlType(varchar), Length(64,true), Default(None) */
    val editedBy: Rep[Option[String]] = column[Option[String]]("edited_by", O.Length(64,varying=true), O.Default(None))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name investment_team_created_at_idx) */
    val index1 = index("investment_team_created_at_idx", createdAt)
    /** Index over (managerUid) (database name investment_team_manager_id_idx) */
    val index2 = index("investment_team_manager_id_idx", managerUid)
    /** Index over (updatedAt) (database name investment_team_updated_at_idx) */
    val index3 = index("investment_team_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table InvestmentTeam */
  lazy val InvestmentTeam = new TableQuery(tag => new InvestmentTeam(tag))

  /** Entity class storing rows of table LegalEntity
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(64,true)
   *  @param capitalSize Database column capital_size SqlType(float8)
   *  @param maxDrawDown Database column max_draw_down SqlType(float8)
   *  @param productCount Database column product_count SqlType(int4)
   *  @param regNum Database column reg_num SqlType(varchar), Length(32,true)
   *  @param estDate Database column est_date SqlType(date)
   *  @param managerName Database column manager_name SqlType(varchar), Length(16,true)
   *  @param site Database column site SqlType(varchar), Length(128,true)
   *  @param description Database column description SqlType(varchar), Length(1024,true)
   *  @param reputation Database column reputation SqlType(varchar), Length(512,true)
   *  @param investTargets Database column invest_targets SqlType(_text), Length(2147483647,false)
   *  @param investStrategies Database column invest_strategies SqlType(varchar), Length(256,true)
   *  @param editedBy Database column edited_by SqlType(varchar), Length(64,true)
   *  @param isDeleted Database column is_deleted SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class LegalEntityRow(uid: String, name: String, capitalSize: Double, maxDrawDown: Double, productCount: Int, regNum: String, estDate: java.sql.Date, managerName: String, site: String, description: String, reputation: String, investTargets: String, investStrategies: String, editedBy: String, isDeleted: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching LegalEntityRow objects using plain SQL queries */
  implicit def GetResultLegalEntityRow(implicit e0: GR[String], e1: GR[Double], e2: GR[Int], e3: GR[java.sql.Date], e4: GR[Boolean], e5: GR[java.sql.Timestamp]): GR[LegalEntityRow] = GR{
    prs => import prs._
    LegalEntityRow.tupled((<<[String], <<[String], <<[Double], <<[Double], <<[Int], <<[String], <<[java.sql.Date], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table legal_entity. Objects of this class serve as prototypes for rows in queries. */
  class LegalEntity(_tableTag: Tag) extends Table[LegalEntityRow](_tableTag, "legal_entity") {
    def * = (uid, name, capitalSize, maxDrawDown, productCount, regNum, estDate, managerName, site, description, reputation, investTargets, investStrategies, editedBy, isDeleted, createdAt, updatedAt) <> (LegalEntityRow.tupled, LegalEntityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(name), Rep.Some(capitalSize), Rep.Some(maxDrawDown), Rep.Some(productCount), Rep.Some(regNum), Rep.Some(estDate), Rep.Some(managerName), Rep.Some(site), Rep.Some(description), Rep.Some(reputation), Rep.Some(investTargets), Rep.Some(investStrategies), Rep.Some(editedBy), Rep.Some(isDeleted), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> LegalEntityRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(64,true) */
    val name: Rep[String] = column[String]("name", O.Length(64,varying=true))
    /** Database column capital_size SqlType(float8) */
    val capitalSize: Rep[Double] = column[Double]("capital_size")
    /** Database column max_draw_down SqlType(float8) */
    val maxDrawDown: Rep[Double] = column[Double]("max_draw_down")
    /** Database column product_count SqlType(int4) */
    val productCount: Rep[Int] = column[Int]("product_count")
    /** Database column reg_num SqlType(varchar), Length(32,true) */
    val regNum: Rep[String] = column[String]("reg_num", O.Length(32,varying=true))
    /** Database column est_date SqlType(date) */
    val estDate: Rep[java.sql.Date] = column[java.sql.Date]("est_date")
    /** Database column manager_name SqlType(varchar), Length(16,true) */
    val managerName: Rep[String] = column[String]("manager_name", O.Length(16,varying=true))
    /** Database column site SqlType(varchar), Length(128,true) */
    val site: Rep[String] = column[String]("site", O.Length(128,varying=true))
    /** Database column description SqlType(varchar), Length(1024,true) */
    val description: Rep[String] = column[String]("description", O.Length(1024,varying=true))
    /** Database column reputation SqlType(varchar), Length(512,true) */
    val reputation: Rep[String] = column[String]("reputation", O.Length(512,varying=true))
    /** Database column invest_targets SqlType(_text), Length(2147483647,false) */
    val investTargets: Rep[String] = column[String]("invest_targets", O.Length(2147483647,varying=false))
    /** Database column invest_strategies SqlType(varchar), Length(256,true) */
    val investStrategies: Rep[String] = column[String]("invest_strategies", O.Length(256,varying=true))
    /** Database column edited_by SqlType(varchar), Length(64,true) */
    val editedBy: Rep[String] = column[String]("edited_by", O.Length(64,varying=true))
    /** Database column is_deleted SqlType(bool), Default(false) */
    val isDeleted: Rep[Boolean] = column[Boolean]("is_deleted", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name legal_entity_created_at_idx) */
    val index1 = index("legal_entity_created_at_idx", createdAt)
    /** Index over (updatedAt) (database name legal_entity_updated_at_idx) */
    val index2 = index("legal_entity_updated_at_idx", updatedAt)
    /** Index over (name) (database name name_idx) */
    val index3 = index("name_idx", name)
  }
  /** Collection-like TableQuery object for table LegalEntity */
  lazy val LegalEntity = new TableQuery(tag => new LegalEntity(tag))

  /** Entity class storing rows of table OtcHedgeFundSecurity
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param productCode Database column product_code SqlType(varchar)
   *  @param productName Database column product_name SqlType(varchar)
   *  @param productFullName Database column product_full_name SqlType(varchar)
   *  @param value Database column value SqlType(float8)
   *  @param ccy Database column ccy SqlType(varchar)
   *  @param loc Database column loc SqlType(varchar)
   *  @param strategyName Database column strategy_name SqlType(varchar)
   *  @param assetName Database column asset_name SqlType(varchar)
   *  @param managerUuid Database column manager_uuid SqlType(varchar), Default(None)
   *  @param buyerUuid Database column buyer_uuid SqlType(varchar), Default(None)
   *  @param sellerUuid Database column seller_uuid SqlType(varchar), Default(None)
   *  @param custodianUuid Database column custodian_uuid SqlType(varchar), Default(None)
   *  @param isStructured Database column is_structured SqlType(bool)
   *  @param validTimeEpoch Database column valid_time_epoch SqlType(int8range), Length(2147483647,false)
   *  @param ownerUuid Database column owner_uuid SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class OtcHedgeFundSecurityRow(uid: String, productCode: String, productName: String, productFullName: String, value: Double, ccy: String, loc: String, strategyName: String, assetName: String, managerUuid: Option[String] = None, buyerUuid: Option[String] = None, sellerUuid: Option[String] = None, custodianUuid: Option[String] = None, isStructured: Boolean, validTimeEpoch: String, ownerUuid: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching OtcHedgeFundSecurityRow objects using plain SQL queries */
  implicit def GetResultOtcHedgeFundSecurityRow(implicit e0: GR[String], e1: GR[Double], e2: GR[Option[String]], e3: GR[Boolean], e4: GR[java.sql.Timestamp]): GR[OtcHedgeFundSecurityRow] = GR{
    prs => import prs._
    OtcHedgeFundSecurityRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[Double], <<[String], <<[String], <<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table otc_hedge_fund_security. Objects of this class serve as prototypes for rows in queries. */
  class OtcHedgeFundSecurity(_tableTag: Tag) extends Table[OtcHedgeFundSecurityRow](_tableTag, "otc_hedge_fund_security") {
    def * = (uid, productCode, productName, productFullName, value, ccy, loc, strategyName, assetName, managerUuid, buyerUuid, sellerUuid, custodianUuid, isStructured, validTimeEpoch, ownerUuid, createdAt, updatedAt) <> (OtcHedgeFundSecurityRow.tupled, OtcHedgeFundSecurityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(productCode), Rep.Some(productName), Rep.Some(productFullName), Rep.Some(value), Rep.Some(ccy), Rep.Some(loc), Rep.Some(strategyName), Rep.Some(assetName), managerUuid, buyerUuid, sellerUuid, custodianUuid, Rep.Some(isStructured), Rep.Some(validTimeEpoch), Rep.Some(ownerUuid), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> OtcHedgeFundSecurityRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10, _11, _12, _13, _14.get, _15.get, _16.get, _17.get, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column product_code SqlType(varchar) */
    val productCode: Rep[String] = column[String]("product_code")
    /** Database column product_name SqlType(varchar) */
    val productName: Rep[String] = column[String]("product_name")
    /** Database column product_full_name SqlType(varchar) */
    val productFullName: Rep[String] = column[String]("product_full_name")
    /** Database column value SqlType(float8) */
    val value: Rep[Double] = column[Double]("value")
    /** Database column ccy SqlType(varchar) */
    val ccy: Rep[String] = column[String]("ccy")
    /** Database column loc SqlType(varchar) */
    val loc: Rep[String] = column[String]("loc")
    /** Database column strategy_name SqlType(varchar) */
    val strategyName: Rep[String] = column[String]("strategy_name")
    /** Database column asset_name SqlType(varchar) */
    val assetName: Rep[String] = column[String]("asset_name")
    /** Database column manager_uuid SqlType(varchar), Default(None) */
    val managerUuid: Rep[Option[String]] = column[Option[String]]("manager_uuid", O.Default(None))
    /** Database column buyer_uuid SqlType(varchar), Default(None) */
    val buyerUuid: Rep[Option[String]] = column[Option[String]]("buyer_uuid", O.Default(None))
    /** Database column seller_uuid SqlType(varchar), Default(None) */
    val sellerUuid: Rep[Option[String]] = column[Option[String]]("seller_uuid", O.Default(None))
    /** Database column custodian_uuid SqlType(varchar), Default(None) */
    val custodianUuid: Rep[Option[String]] = column[Option[String]]("custodian_uuid", O.Default(None))
    /** Database column is_structured SqlType(bool) */
    val isStructured: Rep[Boolean] = column[Boolean]("is_structured")
    /** Database column valid_time_epoch SqlType(int8range), Length(2147483647,false) */
    val validTimeEpoch: Rep[String] = column[String]("valid_time_epoch", O.Length(2147483647,varying=false))
    /** Database column owner_uuid SqlType(varchar) */
    val ownerUuid: Rep[String] = column[String]("owner_uuid")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (assetName) (database name otc_hedge_fund_security_asset_type_name_idx) */
    val index1 = index("otc_hedge_fund_security_asset_type_name_idx", assetName)
    /** Index over (buyerUuid) (database name otc_hedge_fund_security_buyer_uuid_idx) */
    val index2 = index("otc_hedge_fund_security_buyer_uuid_idx", buyerUuid)
    /** Index over (createdAt) (database name otc_hedge_fund_security_created_at_idx) */
    val index3 = index("otc_hedge_fund_security_created_at_idx", createdAt)
    /** Index over (custodianUuid) (database name otc_hedge_fund_security_custodian_uuid_idx) */
    val index4 = index("otc_hedge_fund_security_custodian_uuid_idx", custodianUuid)
    /** Index over (buyerUuid) (database name otc_hedge_fund_security_manager_uuid_idx) */
    val index5 = index("otc_hedge_fund_security_manager_uuid_idx", buyerUuid)
    /** Index over (ownerUuid) (database name otc_hedge_fund_security_owner_uuid_idx) */
    val index6 = index("otc_hedge_fund_security_owner_uuid_idx", ownerUuid)
    /** Uniqueness Index over (productCode) (database name otc_hedge_fund_security_product_code_idx) */
    val index7 = index("otc_hedge_fund_security_product_code_idx", productCode, unique=true)
    /** Index over (productFullName) (database name otc_hedge_fund_security_product_full_name_idx) */
    val index8 = index("otc_hedge_fund_security_product_full_name_idx", productFullName)
    /** Index over (productName) (database name otc_hedge_fund_security_product_name_idx) */
    val index9 = index("otc_hedge_fund_security_product_name_idx", productName)
    /** Index over (sellerUuid) (database name otc_hedge_fund_security_seller_uuid_idx) */
    val index10 = index("otc_hedge_fund_security_seller_uuid_idx", sellerUuid)
    /** Index over (strategyName) (database name otc_hedge_fund_security_strategy_idx) */
    val index11 = index("otc_hedge_fund_security_strategy_idx", strategyName)
    /** Index over (updatedAt) (database name otc_hedge_fund_security_updated_at_idx) */
    val index12 = index("otc_hedge_fund_security_updated_at_idx", updatedAt)
    /** Index over (validTimeEpoch) (database name otc_hedge_fund_security_valid_time_idx) */
    val index13 = index("otc_hedge_fund_security_valid_time_idx", validTimeEpoch)
  }
  /** Collection-like TableQuery object for table OtcHedgeFundSecurity */
  lazy val OtcHedgeFundSecurity = new TableQuery(tag => new OtcHedgeFundSecurity(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param hash Database column hash SqlType(varchar), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(timestamp)
   *  @param applyScript Database column apply_script SqlType(text), Default(None)
   *  @param revertScript Database column revert_script SqlType(text), Default(None)
   *  @param state Database column state SqlType(varchar), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(text), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(varchar), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(timestamp) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(text), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Default(None))
    /** Database column revert_script SqlType(text), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Default(None))
    /** Database column state SqlType(varchar), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(text), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table Task
   *  @param uid Database column uid SqlType(varchar), PrimaryKey
   *  @param title Database column title SqlType(varchar)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class TaskRow(uid: String, title: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching TaskRow objects using plain SQL queries */
  implicit def GetResultTaskRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[TaskRow] = GR{
    prs => import prs._
    TaskRow.tupled((<<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table task. Objects of this class serve as prototypes for rows in queries. */
  class Task(_tableTag: Tag) extends Table[TaskRow](_tableTag, "task") {
    def * = (uid, title, createdAt, updatedAt) <> (TaskRow.tupled, TaskRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uid), Rep.Some(title), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> TaskRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column uid SqlType(varchar), PrimaryKey */
    val uid: Rep[String] = column[String]("uid", O.PrimaryKey)
    /** Database column title SqlType(varchar) */
    val title: Rep[String] = column[String]("title")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (createdAt) (database name task_created_at_idx) */
    val index1 = index("task_created_at_idx", createdAt)
    /** Index over (updatedAt) (database name task_updated_at_idx) */
    val index2 = index("task_updated_at_idx", updatedAt)
  }
  /** Collection-like TableQuery object for table Task */
  lazy val Task = new TableQuery(tag => new Task(tag))
}
