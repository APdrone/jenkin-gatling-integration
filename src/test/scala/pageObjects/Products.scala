package pageObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Products {

  val chairSubCategoryFeeder = csv("data/productDetails_Chairs.csv").random
  val tableSubCategoryFeeder = csv("data/productDetails_Tables.csv").random

  def tableSubCategory = {
    feed(tableSubCategoryFeeder)
      .exec(
        http("Load product Page for ${ProductName}")
          .get("/products/${ProductName}")
          .check(css("input[name='current_product']", "value").saveAs("product_id"))
          .check(status.is(200))
      )
  }

  def chairSubCategory = {
    feed(chairSubCategoryFeeder)
      .exec(
        http("Load product page for  ${ProductName}")
          .get("/products/${ProductName}")
          .check(css("input[name='current_product']", "value").saveAs("product_id"))
          .check(status.is(200))
      )
  }
}
