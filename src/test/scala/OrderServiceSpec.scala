import org.scalatest.{Matchers, FlatSpec}
import ModelProtocol._

class OrderServiceSpec extends FlatSpec with Matchers {


  "Order service" should "have size 0" in {
    assert(OrderService.size == 0)
  }

  it should "return orders if given item exists in orders" in {
    val order1: Order = createOrderBy(List.fill(2)(Apple)::: List.fill(4)(Banana))
    OrderService.addOrder(order1)
    val order2: Order = createOrderBy(List.fill(2)(Melon)::: List.fill(4)(Banana))
    OrderService.addOrder(order2)

    OrderService.searchOrderByItem(Apple) should equal(List(order1))

    OrderService.searchOrderByItem(Banana) should equal(List(order1, order2))

    OrderService.searchOrderByItem(Melon) should equal(List(order2))
  }

  private def createOrderBy(items: List[GroceryItem]): Order = {
    val billItems: List[BillItem] = items.map(item => BillItem(item,0, 0, 0))
    Order(billItems, 0, 0)
  }
}
