package pageObjects

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Order {
  def PlaceOrder = {
    exec(
        http("Place order ")
        .post("/checkout")
        .formParam("ic_formbuilder_redirect", confirmation_Page)
        .formParam("cart_content", "{\"${product_id}__\":" + randomQuantity + "}")
        .formParam("total_net", "${total}")
        .formParam("trans_id", "${transaction_id}")
        .formParam("shipping", "order")
        .formParam("cart_company", companyName)
        .formParam("cart_name",Name)
        .formParam("cart_address", Address)
        .formParam("cart_postal", Postal)
        .formParam("cart_city", City)
        .formParam("cart_country", Country)
        .formParam("cart_phone", Phone)
        .formParam("cart_email", email)
        .formParam("cart_submit", "Place Order")
        .resources(
          http("Confirmation Page")
            .get("/thank-you")
            .check(substring("Thank You"))
        )
    )
  }
}
