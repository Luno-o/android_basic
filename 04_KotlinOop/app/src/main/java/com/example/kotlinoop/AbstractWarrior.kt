package com.example.kotlinoop

import kotlin.random.Random

abstract class AbstractWarrior ( var maxHealth:Int, override var dodgeChance: Int, private val accuracy: Int, private val weapon: AbstractWeapon, var currentHealth: Int): Warrior{

    override fun atack(warrior: Warrior) {
        if (this.weapon.isEmptyClip){
            weapon.reload()
        }else{
            val ammo = weapon.getAmmoForShot()
            if (Random.nextInt(0,100)> this.accuracy && Random.nextInt(0,100) > warrior.dodgeChance){
                warrior.takeDamage(ammo.getCurrentDamage())
            }
        }
    }

    override fun takeDamage(damage: Int) {
        currentHealth -= damage
    }

}