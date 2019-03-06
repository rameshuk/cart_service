
object ModelProtocol {

  sealed trait GroceryItem {
    def value:BigDecimal
    def name:String
    def category:String
  }

  // Fruits, Veggies, Grains and Bakery items.

  // Fruits
  sealed trait Fruit extends GroceryItem {
    override val category = "Fruits"
  }

  case object Apple extends Fruit {
    override val value: BigDecimal = 80.00
    override val name = "Apple"
  }

  case object Banana extends Fruit {
    override val value: BigDecimal = 20.00
    override val name = "Banana"
  }

  case object Melon extends Fruit {
    override val value: BigDecimal = 150.00
    override val name = "Melon"
  }
  private def fruits: List[Fruit] = List[Fruit](Apple, Banana, Melon)

  //Veggies
  sealed trait Veggies extends GroceryItem {
    override val category = "Veggies"

  }

  case object Beans extends Veggies {
    override val name = "Beans"
    override val value = 120.00
  }
  private def veggies: List[Veggies] = List[Veggies](Beans)



  def allItems: List[GroceryItem] = fruits ::: veggies


//  Order Models
  case class BillItem(item: GroceryItem, qty:Int, discount: BigDecimal, totalPrice: BigDecimal) {
    override def toString: String = "\n%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s".format(item.name, item.category, item.value, qty, discount, totalPrice)
  }

  case class Order(billItems: List[BillItem], deliveryCharges: BigDecimal, total:BigDecimal)
}