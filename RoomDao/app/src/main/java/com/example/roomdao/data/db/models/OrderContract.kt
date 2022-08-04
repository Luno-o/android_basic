package com.example.roomdao.data.db.models

object OrderContract {
    const val TABLE_NAME = "orders"
    object Columns{
        const val ID = "id"
        const val CUSTOMER_ID= "customer_id"
        const val STATUS = "status"
        const val CREATED_AT = "created_at"
    }
}