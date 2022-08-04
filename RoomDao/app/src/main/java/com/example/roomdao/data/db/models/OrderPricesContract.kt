package com.example.roomdao.data.db.models

object OrderPricesContract {
    const val TABLE_NAME = "order_prices"
    object Columns{
        const val ORDER_ID = "order_id"
        const val PRODUCT_ID = "product_id"
        const val COUNT = "count"
    }
}