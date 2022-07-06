package com.example.viewmodelandnavigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Transport(
    open val id: Int
):Parcelable{
    @Parcelize
    data class WaterCraft(
        override val id: Int,
        val model:String,
        val imageLink: String,
        val displacement: String
    ):Transport(id)
@Parcelize
    data class AirCraft(
    override val id: Int,
    val model:String,
    val imageLink: String,
    val flightRange: String
    ):Transport(id)
@Parcelize
    data class RoadTransport(
    override val id: Int,
    val model:String,
    val imageLink: String,
    val fuelConsumption: String
    ):Transport(id)
}
