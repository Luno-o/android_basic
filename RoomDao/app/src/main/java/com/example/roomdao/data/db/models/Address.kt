package com.example.roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AddressContract.TABLE_NAME)
data class Address(
    @PrimaryKey
    @ColumnInfo(name = AddressContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = AddressContract.Columns.DELIVERY_ADDRESS)
    val deliveryAddress: String
)