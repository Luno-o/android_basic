package com.example.roomdao.data.db.models

import androidx.room.*

@Entity(tableName = OrderPricesContract.TABLE_NAME
    , foreignKeys = [ForeignKey(
    entity = Order::class,
    parentColumns = [OrderContract.Columns.ID,],
    childColumns = [OrderPricesContract.Columns.ORDER_ID]
),ForeignKey(
    entity = Product::class,
    parentColumns = [ProductContract.Columns.ID],
    childColumns = [OrderPricesContract.Columns.PRODUCT_ID]
)],
    indices = [Index(value = [OrderPricesContract.Columns.ORDER_ID,OrderPricesContract.Columns.PRODUCT_ID],
        unique = true)]
    , primaryKeys =[OrderPricesContract.Columns.ORDER_ID,OrderPricesContract.Columns.PRODUCT_ID]
)
data class OrderPrices(
    @ColumnInfo(name = OrderPricesContract.Columns.ORDER_ID)
    val orderId: Long,
    @ColumnInfo(name = OrderPricesContract.Columns.PRODUCT_ID)
    val productId: Long,
    @ColumnInfo(name = OrderPricesContract.Columns.COUNT)
    val count: Int
)