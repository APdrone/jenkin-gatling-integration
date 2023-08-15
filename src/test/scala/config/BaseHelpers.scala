package config

import scala.util.Random
import scala.concurrent.duration._
import io.gatling.core.Predef._

object BaseHelpers {
  val domain = "localhost"
  val randomQuantity = Random.between(1, 5)
  val minPause = 400.milliseconds
  val maxPause = 900.milliseconds

  def thinkTimer(min:Int=2, max:Int=5)={
    pause(min,max)
  }

  val rnd = new Random();

  def randomString(length: Int): String = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val companyName=randomString(8)
  val Name=randomString(10)
  val Address=randomString(12)
  val Postal=Random.between(1000, 10000)
  val City=randomString(8)
  val Country=randomString(8)
  val Phone=Random.between(1000, 10000)
  val email=randomString(8)+"@mail.com"
  val confirmation_Page="http://localhost/thank-you"
}
