import ModelProtocol.{GroceryItem, _}
import java.io.File
import java.io.FileWriter


class CartService  {

  /*
   create an order and returns total bill amount
   */
  def checkoutBasket[item <: GroceryItem](items: List[item]): BigDecimal = {
    createOrder(items).total
  }

  private def getBillItem(item: GroceryItem, quantity: Int): BillItem = {
    //apply 25% discount
    val absPrice:BigDecimal = item.value * quantity
    val (discount:BigDecimal, totalPrice:BigDecimal) = if (quantity > 2) (0.25 * absPrice, 0.75 * absPrice)
                             else (BigDecimal(0), absPrice)
    BillItem(item, quantity, discount, totalPrice)
  }

  private def createOrder[item <: GroceryItem](items: List[item]): Order = {
    val billItems = items.groupBy(x => x).mapValues(x => x.length).map(x => getBillItem(x._1, x._2)).toList
    val totalBill = billItems.map(x => x.totalPrice).sum
    val deliveryCharges:BigDecimal = if (totalBill > 1000) 50 else 0
    val order: Order = Order(billItems, deliveryCharges, totalBill + deliveryCharges)
    OrderService.addOrder(order)
    printBill(order)
    order
  }

  private def printBill(order: Order): Unit = {
    val file = new File("billBook.txt")
    val writer  = new FileWriter(file, true)
    writer.write("\nOrder details")
    val header = "\n%s\t%s\t%s\t%s\t%s\t%s\t".format("Item Name", "Category", "UnitPrice", "Qty","Discount", "Final Price")
    writer.write(header)
    order.billItems.foreach(billItem => writer.write(billItem.toString))
    writer.write("\nDelivery charges :"+ tabs(8) + order.deliveryCharges)
    writer.write("\nTotal payable    :"+ tabs(8) + order.total)
    writer.close()
  }

  private def tabs(n: Int): String ={
    "\t" * n
  }

  def search(groceryItem: GroceryItem): List[GroceryItem] = {
    allItems.filter(_.isInstanceOf[groceryItem.type])
  }

  def searchByName(name: String): List[GroceryItem] = {
    allItems.filter(_.name == name)
  }

  def searchByCategory(category: String): List[GroceryItem] = {
    allItems.filter(_.category == category)
  }


}




