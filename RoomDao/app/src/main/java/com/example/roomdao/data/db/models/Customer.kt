package com.example.roomdao.data.db.models

import androidx.room.*

@Entity(tableName = CustomerContract.TABLE_NAME
    , foreignKeys = [ForeignKey(
    entity = Address::class,
    parentColumns = [AddressContract.Columns.ID],
    childColumns = [CustomerContract.Columns.DELIVERY_ADDRESS_ID]
)]
)
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CustomerContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = CustomerContract.Columns.DELIVERY_ADDRESS_ID)
    val deliveryAddressId: Long,
    @ColumnInfo(name = CustomerContract.Columns.EMAIL)
    val email: String,
    @ColumnInfo(name = CustomerContract.Columns.CONTACT_PHONE)
    val contactPhone: String,
    @ColumnInfo(name = CustomerContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = CustomerContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = CustomerContract.Columns.AVATAR)
    val avatar: String?,
)