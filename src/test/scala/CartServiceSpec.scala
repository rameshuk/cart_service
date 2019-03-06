import org.scalatest.{Matchers, Outcome, fixture}
import ModelProtocol._

class CartServiceSpec extends fixture.FlatSpec with Matchers {

  override type FixtureParam = CartService

  override protected def withFixture(test: OneArgTest): Outcome = test(new CartService)


  behavior of "Cart service"

  it should "return zero bill for empty basket" in {
    s => s.checkoutBasket(List.empty[GroceryItem]) should equal(0.00)
  }

  it should "return 370 bill for (Apple, Banana, Melon, Beans) basket" in {
    s => {
      val basket = List[GroceryItem](Apple, Banana, Melon, Beans)
      s.checkoutBasket(basket) should equal(370.00)
    }
  }

  it should "return 530 bill for (Apple(4), Banana(1),Beans(3)) basket with 25% discount on Apples and Beans" in {
    s => {
      val basket = List.fill(4)(Apple):::List.fill(3)(Beans) ::: List.fill(1)(Banana)
      //4*80 = 320 (80+240)  + 3*120 = 360 (90+270) + 1* 20 = 530
      s.checkoutBasket(basket) should equal(530.00)
    }
  }
  it should "return 2375 bill for (Apple(20), Melon(10)) basket with 25% discount and delivery charges" in {
    s => {
      val basket = List.fill(20)(Apple):::List.fill(10)(Melon)
      //20*80 = 1600 (400+1200)  + 10*150 = 1500 (375+1125) + delivery 20 = 2375
      s.checkoutBasket(basket) should equal(2375.00)
    }
  }

}
