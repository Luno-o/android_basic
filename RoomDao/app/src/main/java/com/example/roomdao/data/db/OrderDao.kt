package com.example.roomdao.data.db

import androidx.room.*
import com.example.roomdao.data.db.models.Order
import com.example.roomdao.data.db.models.OrderContract

@Dao
interface OrderDao {

@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: List<Order>)


    @Delete
    suspend fun removeOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT * FROM ${OrderContract.TABLE_NAME}")
    suspend fun getOrders(): List<Order>


//    @Transaction
//    @Query("SELECT * FROM ${OrderContract.TABLE_NAME} WHERE ${OrderContract.Columns.ID} = :id")
//    suspend fun getOrderWithPrices(id:Long): OrderWithPrices
//    @Query(
//        "SELECT * FROM user" +
//                "JOIN book ON user.id = book.user_id"
//    )
//    fun loadUserAndBookNames(): Map<User, List<Book>>
}