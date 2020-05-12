package com.example.kotlinoop

class Sergeant(
    maxHealth: Int, dodgeChance: Int, accuracy: Int, weapon: AbstractWeapon, currentHealth: Int
) : AbstractWarrior(
    maxHealth, dodgeChance,
    accuracy, weapon, currentHealth
) {

    companion object {
        fun createSergeant(): Sergeant {
            return Sergeant(2000, 20, 70, Weapons.Ak47,  2000)
        }
    }
}