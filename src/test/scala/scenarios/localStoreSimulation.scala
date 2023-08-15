package scenarios

import config.BaseHelpers._

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import pageObjects._

class localStoreSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://"+domain);

  def userCount: Int = getProperty("USERS", "5").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  def userFinalCount: Int = getProperty("USERS_FINAL", "100").toInt
  def testDuration: Int = getProperty("DURATION", "60").toInt


  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  object UserJourneys{
    def browseStore={
      exec(Home.homePage)
        .pause(maxPause)
        .exec(Category.tableTab)
        .exec(thinkTimer())
        .repeat(2){
          exec(Products.tableSubCategory)
            .exec(thinkTimer())
        }
    }
    def abandonCartTable={
      exec(Home.homePage)
        .pause(maxPause)
        .exec(Category.tableTab)
        .exec(thinkTimer())
        .exec(Products.tableSubCategory)
        .exec(thinkTimer())
        .exec(Cart.addToCart)
    }
    def abandonCartChair={
      exec(Home.homePage)
        .pause(maxPause)
        .exec(Category.chairTab)
        .exec(thinkTimer())
        .exec(Products.chairSubCategory)
        .exec(thinkTimer())
        .exec(Cart.addToCart)
    }
    def completePurchase={
      exec(Home.homePage)
        .pause(maxPause)
        .exec(Category.tableTab)
        .exec(thinkTimer())
        .exec(Products.tableSubCategory)
        .exec(thinkTimer())
        .exec(Cart.addToCart)
        .exec(thinkTimer())
        .exec(Cart.viewCart)
        .exec(thinkTimer())
        .exec(Cart.checkout)
        .exec(thinkTimer())
        .exec(Order.PlaceOrder)
    }
  }

  object Scenarios{
    def default=scenario("Default Load Test")
          .exec(flushHttpCache)
          .exec(flushCookieJar)
      .exitBlockOnFail(
      during(testDuration.seconds) {
        randomSwitch(
          10d -> exec(UserJourneys.browseStore),
          10d -> exec(UserJourneys.abandonCartTable),
          50d -> exec(UserJourneys.abandonCartChair),
          30d -> exec(UserJourneys.completePurchase)
        )
      }
      )

    def highPurchase= scenario("High purchase load test")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
      during(testDuration.seconds) {
        randomSwitch(
          25d -> exec(UserJourneys.browseStore),
          25d -> exec(UserJourneys.abandonCartChair),
          50d -> exec(UserJourneys.completePurchase)
        )
      }
      )
  }

  /*for open model*/

    setUp(
      Scenarios.default
        .inject(rampUsers(userCount) during (rampDuration.seconds)).protocols(httpProtocol)
    )

  /*  for close model  */

//    setUp(scn.inject(
//      rampConcurrentUsers(userCount) to(userFinalCount) during (rampDuration.seconds)
//    ).protocols(httpProtocol)
//    )

   /* Command for running test  */


  //for open
  // mvn gatling:test -DUSERS=100 -DRAMP-DURATION=60 -DDURATION=60 "-Dgatling.simulationClass=scenarios.localStoreSimulation"

  //for closed
  // mvn gatling:test -DUSERS=10 _DUSERS_FINAL=100 -DRAMP-DURATION=60 -DDURATION=60 "-Dgatling.simulationClass=scenarios.localStoreSimulation"


}
