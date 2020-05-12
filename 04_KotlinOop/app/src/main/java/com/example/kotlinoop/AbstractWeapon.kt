package com.example.kotlinoop


abstract class AbstractWeapon(private val maxAmmo: Int, private val fireType: FireType) {

    var listAmmo: MutableList<Ammo> = mutableListOf()

    var isEmptyClip: Boolean = listAmmo.isEmpty()

    abstract fun makeAmmo(): Ammo

    fun reload() {
        listAmmo = mutableListOf()
        while (this.listAmmo.size < this.maxAmmo) {
            listAmmo.add(makeAmmo())
        }
        isEmptyClip = listAmmo.isEmpty()
    }

    fun getAmmoForShot(): Ammo {
        val ammo: Ammo = listAmmo[0]
        if (this.fireType == FireType.SingleShot && listAmmo.size > 0) {
            listAmmo.removeAt(0)
        } else if (this.fireType == FireType.Bursting && listAmmo.size > 2) {
            listAmmo.removeAt(0)
            listAmmo.removeAt(0)
            listAmmo.removeAt(0)
        } else {
        }
        return ammo
    }
}

object Weapons {
    val pistol = object : AbstractWeapon(12, FireType.SingleShot) {
        override fun makeAmmo(): Ammo {
            return Ammo.CUSTOMBULLET
        }

    }
    val Ak47 = object : AbstractWeapon(30, FireType.Bursting) {
        override fun makeAmmo(): Ammo {
            return Ammo.BULLETDISPLAYSEDCENTERGRAVITY
        }

    }
    val rifle = object : AbstractWeapon(20, FireType.SingleShot) {
        override fun makeAmmo(): Ammo {
            return Ammo.FULLMETALSHELLBULLET
        }

    }
    val miniGun = object : AbstractWeapon(100, FireType.Bursting) {
        override fun makeAmmo(): Ammo {
            return Ammo.TRACER
        }

    }
}