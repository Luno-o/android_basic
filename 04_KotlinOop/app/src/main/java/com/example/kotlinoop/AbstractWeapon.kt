package com.example.kotlinoop


abstract class AbstractWeapon(private val maxAmmo: Int, private val fireType: FireType) {

    var listAmmo: MutableList<Ammo> = mutableListOf()

    var isEmptyClip: Boolean = listAmmo.isEmpty()
    get() {return listAmmo.isEmpty()}

    abstract fun makeAmmo(): Ammo

    fun reload() {
        listAmmo = mutableListOf()
        while (this.listAmmo.size < this.maxAmmo) {
            listAmmo.add(makeAmmo())
        }
        isEmptyClip = listAmmo.isEmpty()
    }

    fun getAmmoForShot(): List<Ammo> {
        val ammo: MutableList<Ammo> = mutableListOf()
        if (this.fireType == FireType.SingleShot && listAmmo.size > 0) {
            for (i in 1..FireType.SingleShot.shotCount){
                ammo.add(listAmmo[0])
                listAmmo.removeAt(0)
            }

        } else if (this.fireType == FireType.Bursting && listAmmo.size > 2) {
            for (i in 1..FireType.Bursting.shotCount){
                if(!listAmmo.isEmpty()){
                    ammo.add(listAmmo[0])
                    listAmmo.removeAt(0)
                }
            }
        }
        return ammo
    }
}

