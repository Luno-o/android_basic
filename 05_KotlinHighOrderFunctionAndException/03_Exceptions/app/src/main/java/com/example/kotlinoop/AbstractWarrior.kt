package com.example.kotlinoop

import kotlin.random.Random

abstract class AbstractWarrior(
    private var maxHealth: Int,
    override var dodgeChance: Int,
    private val accuracy: Int,
    private val weapon: AbstractWeapon,
    var currentHealth: Int
) : Warrior {

    override var isKilled: Boolean = false
        get() {
            return currentHealth == 0
        }

    override fun atack(warrior: Warrior) {
        this.weapon.isEmptyClip= this.weapon.listAmmo.isEmpty()
        if (this.weapon.isEmptyClip) {
            weapon.reload()
        } else {
            val ammo = weapon.getAmmoForShot()
            if (Random.nextInt(0, 100) > this.accuracy && Random.nextInt(
                    0,
                    100
                ) > warrior.dodgeChance
            ) {
                for (i in ammo){
                    warrior.takeDamage(i.getCurrentDamage())
                }

            }
        }
    }

    override fun takeDamage(damage: Int) {
        currentHealth -= damage
        if (currentHealth < 0)currentHealth = 0
    }

}