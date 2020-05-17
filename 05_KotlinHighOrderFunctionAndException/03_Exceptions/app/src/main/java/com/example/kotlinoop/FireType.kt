package com.example.kotlinoop

sealed class FireType(val shotCount: Int) {

    object SingleShot : FireType(1)
    object Bursting : FireType(3)
}
