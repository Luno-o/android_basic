package com.example.kotlinoop

class Captain(
    maxHealth: Int,
    dodgeChance: Int,
    accuracy: Int,
    weapon: AbstractWeapon,
    currentHealth: Int
) : AbstractWarrior(
    maxHealth, dodgeChance,
    accuracy, weapon, currentHealth
) {

    companion object {
        fun createCaptain(): Captain {
            return Captain(2500, 30, 80, Weapons.miniGun, 2500)
        }
    }
}
