package com.example.roomdao.data.db

import androidx.room.TypeConverter
import com.example.roomdao.data.db.models.OrderStatuses
import org.threeten.bp.Instant

class OrderStatusConverter {
@TypeConverter
    fun convertStatusToString(status: OrderStatuses):String = status.name

@TypeConverter
    fun convertStringToStatus(string: String): OrderStatuses = OrderStatuses.valueOf(string)

    @TypeConverter
    fun convertInstantToString(instant: Instant):String = instant.epochSecond.toString()

    @TypeConverter
    fun convertStringToInstant(string: String):Instant = Instant.ofEpochSecond(string.toLong(),0)
}