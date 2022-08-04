package com.example.roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdao.data.db.models.*

@Database(entities = [
    Address::class,
    Customer::class ,
    Order::class,
    Product::class,
OrderPrices::class
                     ], version = SkillBoxDatabase.DB_VERSION)
abstract class SkillBoxDatabase:RoomDatabase() {
abstract fun addressDao(): AddressDao
    abstract fun customerDao():CustomerDao
abstract fun orderDao(): OrderDao
abstract fun orderPricesDao(): OrderPricesDao
abstract fun productDao(): ProductDao
    companion object{
        const val  DB_VERSION = 1
        const val DB_NAME = "skillbox-database"
    }
}