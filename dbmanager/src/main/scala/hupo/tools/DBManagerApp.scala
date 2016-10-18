package hupo.tools

import java.io.{File, FileNotFoundException}

import org.slf4j.LoggerFactory
import play.api.db.DBApi
import play.api.db.evolutions.{EnvironmentEvolutionsReader, Evolutions}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Configuration, Environment, Mode}
import slick.codegen.SourceCodeGenerator

/**
  * Created by kunjiang on 16/8/14.
  */
case class CmdLine(rootDir : String = "./", db: String = "default", enableEvolution: Boolean = true, enableGenerator: Boolean = true)

object DBManagerApp extends App {
  // -root /Users/kunjiang/projects/dd/dbmanager -db dev
  val parser = new scopt.OptionParser[CmdLine]("dbManager") {
    head("db Manager", "0.x")

    opt[String]('r', "root").action((x, c) =>
      c.copy(rootDir = x)).text("Root dir of evolution config");

    opt[Boolean]('e', "evolution").action((x, c) =>
      c.copy(enableEvolution = x)).text("Turn evolution on/off");

    opt[Boolean]('g', "generator").action((x, c) =>
      c.copy(enableGenerator = x)).text("Turn generator on/off");

    opt[String]('d', "db").action((x, c) =>
      c.copy(db = x)).text("Database name evolution need apply");
  }

  parser.parse(args, CmdLine()) match {
    case Some(config) => {
      val env = Environment.simple(new File(config.rootDir), Mode.Dev)
      if (config.enableEvolution) {
        doEvolutionOperation(env, config);
      }

      if (config.enableGenerator) {
        doCodeGen(env, config);
      }
    }
    case None =>
  }


  def doEvolutionOperation(env: Environment, cmdLine: CmdLine): Unit = {

    val evolutionFileDirectory = s"${cmdLine.rootDir}/conf/evolutions/${cmdLine.db}"
    System.out.println(s"----- Searching evolution file in ${evolutionFileDirectory}")
    val file = new File(evolutionFileDirectory);
    if (!file.exists() || !file.isDirectory) {
      throw new FileNotFoundException(s"${evolutionFileDirectory} not found or not directory");
    }
    // DB evolution
    lazy val appBuilder = new GuiceApplicationBuilder().loadConfig(Configuration.load(env))
    lazy val injector = appBuilder.injector()
    lazy val databaseApi = injector.instanceOf[DBApi]
    val database = databaseApi.database(cmdLine.db)
    System.out.println(s"----- Starting to apply evolution for ${database.url}")
    Thread.sleep(100)

    Evolutions.applyEvolutions(database = database, evolutionsReader = new EnvironmentEvolutionsReader(env), autocommit = false)

  }

  def doCodeGen(env: Environment, cmdLine: CmdLine): Unit = {
    // Code generation
    val db = cmdLine.db
    val slickPrefix = "slick.dbs"
    val slickCodeGenDatabaseUrl = Configuration.load(env).getString(s"$slickPrefix.$db.db.url").get
    val slickCodeGenDatabaseUser = Configuration.load(env).getString(s"$slickPrefix.$db.db.user").get
    val slickCodeGenDatabasePassword = Configuration.load(env).getString(s"$slickPrefix.$db.db.password").get
    val slickCodeGenJdbcDriver = Configuration.load(env).getString(s"$slickPrefix.$db.db.driver").get

    val slickCodeGenDriver = Configuration.load(env).getString(s"$slickPrefix.$db.codeGen.driver").get
    val slickCodeGenOutputDir = Configuration.load(env).getString(s"$slickPrefix.$db.codeGen.outputDir").get
    val packageName = Configuration.load(env).getString(s"$slickPrefix.$db.codeGen.package").get

    System.out.println(s"Start code generator for ${slickCodeGenDatabaseUrl}, output path ${slickCodeGenOutputDir}, package ${packageName}, " +
      s"code gen driver: ${slickCodeGenDriver}, jdbc driver ${slickCodeGenJdbcDriver}")
    SourceCodeGenerator.run(slickDriver = slickCodeGenDriver,
      jdbcDriver = slickCodeGenJdbcDriver,
      url = slickCodeGenDatabaseUrl,
      outputDir = slickCodeGenOutputDir,
      pkg = packageName,
      user = Some(slickCodeGenDatabaseUser),
      password = Some(slickCodeGenDatabasePassword))
  }
}
