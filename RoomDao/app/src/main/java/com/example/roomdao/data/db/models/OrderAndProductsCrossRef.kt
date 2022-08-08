package com.example.roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [OrderContract.Columns.ID,ProductContract.Columns.ID])
data class OrderAndProductsCrossRef(
    @ColumnInfo(name = OrderContract.Columns.ID)
    val orderId: Long,
    @ColumnInfo(name = ProductContract.Columns.ID)
    val productId: Long
)