package com.example.roomdao.data

import com.example.roomdao.data.db.Database

import com.example.roomdao.data.db.models.Order


class OrderRepository {
    private val orderDao = Database.instance.orderDao()


    suspend fun getAllOrders():List<Order>{
        return orderDao.getOrders()
    }
    suspend fun removeOrder(order: Order){
        orderDao.removeOrder(order)
    }
    suspend fun saveOrder(order: Order){
        orderDao.insertOrder(listOf(order))
    }



}