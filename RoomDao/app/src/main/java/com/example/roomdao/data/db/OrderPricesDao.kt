package com.example.roomdao.data.db

import androidx.room.*
import com.example.roomdao.data.db.models.Order
import com.example.roomdao.data.db.models.OrderPrices
import com.example.roomdao.data.db.models.OrderPricesContract

@Dao
interface OrderPricesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrderPrices(orderPrices: OrderPrices)

    @Delete
    suspend fun removeOrderPrice(orderPrices: OrderPrices)

    @Update
    suspend fun updateOrderPrice(orderPrices: OrderPrices)

    @Query("SELECT * FROM ${OrderPricesContract.TABLE_NAME}")
    suspend fun getAllOrderPrices():List<OrderPrices>

    @Query("SELECT * FROM ${OrderPricesContract.TABLE_NAME} WHERE ${OrderPricesContract.Columns.ORDER_ID} = :orderId")
    suspend fun getOrderPrices(orderId: Long):List<OrderPrices>

}