package com.example.roomdao.data

import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.Order
import com.example.roomdao.data.db.models.OrderPrices

class OrderPricesRepository {
    private val orderPricesDao = Database.instance.orderPricesDao()


    suspend fun getAllOrders():List<OrderPrices>{
        return orderPricesDao.getAllOrderPrices()
    }
    suspend fun removeOrder(orderPrices: OrderPrices){
        orderPricesDao.removeOrderPrice(orderPrices)
    }
    suspend fun saveOrder(orderPrices: OrderPrices){
        orderPricesDao.addOrderPrices(orderPrices)
    }
}