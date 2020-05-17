package com.example.kotlinoop

class Corporal(
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
        fun createCorporal(): Corporal {
            return Corporal(1500, 15, 60, Weapons.rifle, 1500)
        }
    }
}
