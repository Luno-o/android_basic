package com.example.roomdao.data.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithAddress(
    @Embedded
    val address: Address,
    @Relation(
        parentColumn = AddressContract.Columns.ID,
        entityColumn = CustomerContract.Columns.DELIVERY_ADDRESS_ID
    )
    val customer: Customer
)