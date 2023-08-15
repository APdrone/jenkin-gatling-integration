package pageObjects

import config.BaseHelpers.randomQuantity
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Cart {

  def addToCart = {
    exec(
      http("Add to Cart")
        .post("/wp-admin/admin-ajax.php")
        .formParam("action", "ic_add_to_cart")
        .formParam("add_cart_data", "current_product=${product_id}&cart_content=&current_quantity=" + randomQuantity)
        .formParam("cart_widget", "0")
        .formParam("cart_container", "0")
    )
  }

  def viewCart = {
    exec(
      http("Load Cart Page")
        .get("/cart")
        .check(css("input[name='trans_id']", "value").saveAs("transaction_id"))
        .check(css("input[name='total_net']", "value").saveAs("total"))
        .check(substring("Cart"))
    )
  }

  def checkout = {
    exec(
      http("Open Checkout Page")
        .post("/checkout")
        .formParam("cart_content", "{\"${product_id}__\":" + randomQuantity + "}")
        .formParam("p_id[]", "${product_id}" + "__")
        .formParam("p_quantity[]", randomQuantity)
        .formParam("total_net", "${total}")
        .formParam("trans_id", "${transaction_id}")
        .formParam("shipping", "order")
        .check(status.is(200))
    )

  }

}
