import ModelProtocol._

import scala.collection.mutable.ListBuffer

object OrderService {

  var allOrders = ListBuffer.empty[Order]

  def addOrder(order: Order): Unit ={
    allOrders += order
  }

  def size =  allOrders.length

  def searchOrderByItem(item: GroceryItem): List[Order] = {
    allOrders.filter(order =>{ order.billItems.exists(_.item == item)}).toList
  }

}
