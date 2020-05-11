package com.example.kotlinoop

class Corporal(
    maxHealth: Int, dodgeChance: Int, accuracy: Int, weapon: AbstractWeapon, currentHealth: Int
    ):AbstractWarrior(maxHealth, dodgeChance,
    accuracy, weapon, currentHealth
){
    override var isKilled: Boolean = currentHealth <= 0
    companion object{
        fun createCorporal():Corporal{
            return Corporal(1500,15,60,Weapons.rifle,currentHealth = 1500)
        }
    }
}