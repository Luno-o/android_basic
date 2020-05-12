package com.example.kotlinoop

import kotlin.random.Random

class Battle(var team1: Team, var team2: Team) {
    var isAliveTeam1:Boolean = true
    var isAliveTeam2:Boolean = true
    var endBattle: Boolean = false

    fun getBattleState(): BattleState {
        return if (isAliveTeam1 && isAliveTeam2) {
            BattleState.CurrentProgress(team1, team2)
        } else if (!isAliveTeam1 && !isAliveTeam2) {
            BattleState.Draw(team1, team2)

        } else if (!isAliveTeam1 && isAliveTeam2) {
            BattleState.Team2Win(team1, team2)
        } else {
            BattleState.Team1Win(team1, team2)
        }
    }

    fun nextBattleTurn() {
        team1.warriorsList.shuffle()
        team2.warriorsList.shuffle()
        val minRange: Int = if (team1.warriorsList.size > team2.warriorsList.size) {
            team2.warriorsList.size
        } else {
            team1.warriorsList.size
        }
        for (i: Int in 0 until minRange) {
            val turnOfAttack: Boolean = Random.nextBoolean()
            if (turnOfAttack) {
                team1.warriorsList[i].atack(team2.warriorsList[i])
                team2.warriorsList[i].isKilled = team2.warriorsList[i].currentHealth <= 0
                if (!team2.warriorsList[i].isKilled) {
                    team2.warriorsList[i].atack(team1.warriorsList[i])
                } else {
                    continue
                }
            } else {
                team2.warriorsList[i].atack(team1.warriorsList[i])
                team1.warriorsList[i].isKilled = team1.warriorsList[i].currentHealth <= 0
                if (!team1.warriorsList[i].isKilled) {
                    team1.warriorsList[i].atack(team2.warriorsList[i])
                } else {
                    continue
                }
            }
        }
        val team1Copy = mutableListOf<AbstractWarrior>()
        team1Copy.addAll(team1.warriorsList)
        team1Copy.forEach {
            if (it.isKilled) {
                if (team1.warriorsList.size > 1){

                team1.warriorsList.remove(it)
                } else if(team1.warriorsList.size == 1) {
                    isAliveTeam1 = false
                    endBattle = true
            }
            }
        }
        val team2Copy = mutableListOf<AbstractWarrior>()
        team2Copy.addAll(team2.warriorsList)
        team2Copy.forEach {
            if (it.isKilled) {
                if (team2.warriorsList.size>1 ){
                    team2.warriorsList.remove(it)

                }else if(team2.warriorsList.size == 1) {
                    isAliveTeam2 = false
                    endBattle = true
            }
            }
        }
    }

}