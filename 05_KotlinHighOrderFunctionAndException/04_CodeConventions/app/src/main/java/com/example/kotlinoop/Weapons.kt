package com.example.kotlinoop

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
