package pageObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Home {
  def homePage = {
    exec(
      http("Load Home Page")
        .get("/")
        .check(status.is(200))
        .check(substring("All Products"))
    )
  }
}
