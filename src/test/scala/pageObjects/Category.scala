package pageObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Category {

  val chairSubCategoryFeeder = csv("data/productDetails_Chairs.csv").random
  val tableSubCategoryFeeder = csv("data/productDetails_Tables.csv").random

  def tableTab = {
    feed(tableSubCategoryFeeder)
      .exec(
        http("Load Category Page for - ${CategoryName}")
          .get("/products-category/${CategoryName}")
          .check(substring("All Tables"))
      )
  }

  def chairTab = {
    feed(chairSubCategoryFeeder)
      .exec(
        http("Load Category Page for - ${CategoryName}")
          .get("/products-category/${CategoryName}")
          .check(substring("All Chairs"))
      )

  }
}
