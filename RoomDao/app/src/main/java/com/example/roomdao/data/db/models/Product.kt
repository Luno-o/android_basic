package com.example.roomdao.data.db.models

import androidx.room.*

@Entity(tableName = ProductContract.TABLE_NAME)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProductContract.Columns.ID)
    val id:Long,
    @ColumnInfo(name = ProductContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = ProductContract.Columns.PRICE)
    val price: Int,
    @ColumnInfo(name = ProductContract.Columns.AVATAR)
    val avatar: String?,
    @ColumnInfo(name = ProductContract.Columns.DESCRIPTION)
    val description: String
)

data class ProductWithCount(
    val title: String,
    val price: Int,
    val avatar: String?,
    val description: String,
    val count: Int
)