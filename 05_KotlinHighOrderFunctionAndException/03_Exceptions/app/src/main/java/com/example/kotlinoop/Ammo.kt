package com.example.kotlinoop

import kotlin.random.Random

enum class Ammo(
    private val damage: Int,
    private val critChance: Int,
    private val critDamageRatio: Int
) {

    CUSTOMBULLET(10, 10, 130),
    FULLMETALSHELLBULLET(12, 30, 150),
    BULLETDISPLAYSEDCENTERGRAVITY(8, 80, 300),
    TRACER(10, 50, 200);

    fun getCurrentDamage(): Int {

        return if (Random.nextInt(0, 100) < critChance) {
            damage * critDamageRatio / 100
        } else {
            damage
        }
    }

}