package com.example.roomdao.data.db.models

object ProductContract {
    const val TABLE_NAME = "purchases"
    object Columns{
        const val ID = "product_id"
        const val TITLE = "title"
        const val PRICE = "price"
        const val DESCRIPTION = "description"
        const val AVATAR = "avatar"
    }

}