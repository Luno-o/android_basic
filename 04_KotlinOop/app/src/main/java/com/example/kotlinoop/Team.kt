package com.example.kotlinoop

import kotlin.random.Random


class Team {
    var warriorsList = mutableListOf<AbstractWarrior>()
    val warriorCount = readLine()?.toIntOrNull() ?: 0
    private fun fillWarriorsList() {
        while (warriorsList.size < this.warriorCount) {
            val chance = Random.nextInt(0, 100)
            if (chance in 0..49) {
                warriorsList.add(OrdinarySoldier.createSoldier())
            }
            if (chance in 50..69) {
                warriorsList.add(Corporal.createCorporal())
            }
            if (chance in 70..89) {
                warriorsList.add(Sergeant.createSergeant())
            }
            if (chance in 90..100) {
                warriorsList.add(Captain.createCaptain())
            }
        }
    }

    init {
        fillWarriorsList()
    }
}