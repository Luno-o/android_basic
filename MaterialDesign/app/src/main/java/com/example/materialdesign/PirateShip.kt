package com.example.materialdesign

import androidx.annotation.DrawableRes

var shipList = mutableListOf<PirateShip>()
data class PirateShip(
    val title: String,
    val description:String,
    val image :String,
    val id: Int? = shipList.size
) {

}