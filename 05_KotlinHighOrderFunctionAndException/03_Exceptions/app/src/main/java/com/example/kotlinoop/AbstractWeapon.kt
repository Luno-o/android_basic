package com.example.kotlinoop

abstract class AbstractWeapon(private val maxAmmo: Int, private val fireType: FireType) {

    var listAmmo: MutableList<Ammo> = mutableListOf()

    var isEmptyClip: Boolean = listAmmo.isEmpty()
        get() {
            return listAmmo.isEmpty()
        }

    abstract fun makeAmmo(): Ammo

    fun reload() {
        listAmmo = mutableListOf()
        while (this.listAmmo.size < this.maxAmmo) {
            listAmmo.add(makeAmmo())
        }
    }

    fun getAmmoForShot(): List<Ammo> {
        val ammo: MutableList<Ammo> = mutableListOf()
        try {
            if (this.fireType == FireType.SingleShot) {
                if (isEmptyClip) {
                    throw NoAmmoException()
                }
                ammo.add(listAmmo.removeAt(0))
            } else if (this.fireType == FireType.Bursting) {
                for (i in 1..FireType.Bursting.shotCount) {
                    if (isEmptyClip) {
                        throw NoAmmoException()
                    }
                    ammo.add(listAmmo.removeAt(0))
                }
            }
        } catch (t: NoAmmoException) {
            reload()
        }

        return ammo
    }
}
