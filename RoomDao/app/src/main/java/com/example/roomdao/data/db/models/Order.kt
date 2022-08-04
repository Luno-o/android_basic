package com.example.roomdao.data.db.models

import androidx.room.*
import com.example.roomdao.data.db.OrderStatusConverter
import org.threeten.bp.Instant

@Entity(tableName = OrderContract.TABLE_NAME
    , foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = [CustomerContract.Columns.ID],
        childColumns = [OrderContract.Columns.CUSTOMER_ID]
    )]
)
@TypeConverters(OrderStatusConverter::class)
data class Order(
    @ColumnInfo(name = OrderContract.Columns.ID)
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = OrderContract.Columns.CUSTOMER_ID)
    val customerId:Long,
    @ColumnInfo(name = OrderContract.Columns.STATUS)
    val status: OrderStatuses
    ,
    @ColumnInfo(name = OrderContract.Columns.CREATED_AT)
    val createdAt: Instant
)