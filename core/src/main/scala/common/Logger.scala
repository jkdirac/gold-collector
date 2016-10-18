package common

import org.slf4j.LoggerFactory

/**
  * Created by kunjiang on 16/8/2.
  */
trait Logger {
  protected val logger = LoggerFactory.getLogger("play")  // current logback.xml support play only
}

