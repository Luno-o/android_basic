package com.example.kotlinoop

class Sergeant(
    maxHealth: Int, dodgeChance: Int, accuracy: Int, weapon: AbstractWeapon, currentHealth: Int
) : AbstractWarrior(
    maxHealth, dodgeChance,
    accuracy, weapon, currentHealth
) {
    override var isKilled: Boolean = currentHealth <= 0

    companion object {
        fun createSergeant(): Sergeant {
            return Sergeant(2000, 20, 70, Weapons.Ak47, currentHealth = 2000)
        }
    }
}