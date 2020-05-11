package com.example.kotlinoop

sealed class FireType {

    object SingleShot: FireType()
    object Bursting: FireType()

}