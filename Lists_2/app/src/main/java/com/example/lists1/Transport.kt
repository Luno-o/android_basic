package com.example.lists1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Transport:Parcelable{
    @Parcelize
    data class WaterCraft(
        val id: Long,
        val model:String,
        val imageLink: String,
        val displacement: String
    ):Transport()
@Parcelize
    data class AirCraft(
    val id: Long,
        val model:String,
        val imageLink: String,
        val flightRange: String
    ):Transport()
@Parcelize
    data class RoadTransport(
    val id: Long,
        val model:String,
        val imageLink: String,
        val fuelConsumption: String
    ):Transport()
}
