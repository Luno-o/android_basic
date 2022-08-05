package com.example.roomdao.data

import com.example.roomdao.data.db.Database

import com.example.roomdao.data.db.models.Order
import com.example.roomdao.data.db.models.OrderStatuses
import com.example.roomdao.data.db.models.OrderWithProducts
import org.threeten.bp.Instant


class OrderRepository {
    private val orderDao = Database.instance.orderDao()
//private val thisOrder: Order
//get()=


suspend fun getOrderWithProducts(id:Long):OrderWithProducts{
    return orderDao.getOrderWithPrices(id)
}
    suspend fun getAllOrders():List<Order>{
        return orderDao.getOrders()
    }
    suspend fun removeOrder(order: Order){
        orderDao.removeOrder(order)
    }
    suspend fun saveOrder(order: Order){
        orderDao.insertOrder(listOf(order))
    }
    suspend fun updateOrder(order: Order){
        orderDao.updateOrder(order)
    }
suspend fun getActiveOrder(): Order{
    val order = getAllOrders().last()
    return if(order.status==OrderStatuses.FINISHED){
        val newOrder = Order(0,1,OrderStatuses.CREATED, Instant.now())
        saveOrder(newOrder)
        newOrder
    }else order
}

}