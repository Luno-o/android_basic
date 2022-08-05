package com.example.roomdao.data.db

import androidx.room.*
import com.example.roomdao.data.db.models.Product
import com.example.roomdao.data.db.models.ProductContract

@Dao
interface ProductDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun removeProduct(product: Product)

    @Query("SELECT * FROM ${ProductContract.TABLE_NAME}")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM ${ProductContract.TABLE_NAME} WHERE ${ProductContract.Columns.ID} = :productId")
    suspend fun getProductById(productId: Long): Product?
}