package com.example.kotlinoop

class OrdinarySoldier(
    maxHealth: Int,
    dodgeChance: Int,
    accuracy: Int,
    weapon: AbstractWeapon,
    currentHealth: Int
) : AbstractWarrior(
    maxHealth, dodgeChance, accuracy, weapon, currentHealth
) {

    companion object {
        fun createSoldier(): OrdinarySoldier {
            return OrdinarySoldier(
                maxHealth = 1000,
                dodgeChance = 10,
                accuracy = 50,
                weapon = Weapons.pistol,
                currentHealth = 1000
            )
        }
    }
}
