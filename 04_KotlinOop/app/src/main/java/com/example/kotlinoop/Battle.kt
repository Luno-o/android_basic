package com.example.kotlinoop

import kotlin.random.Random

class Battle(var team1: Team, var team2: Team) {

    var endBattle: Boolean = getBattleState() == BattleState.Team1Win(
        team1,
        team2
    ) || getBattleState() == BattleState.Team2Win(team1, team2)

    fun getBattleState(): BattleState {
        var isAliveTeam1 = team1.warriorsList.isEmpty()
        var isAliveTeam2 = team2.warriorsList.isEmpty()
        if (!isAliveTeam1 && !isAliveTeam2) {
            return BattleState.CurrentProgress(team1, team2)
        }
        if (isAliveTeam1 && isAliveTeam2) {
            return BattleState.Draw(team1, team2)
        }
        if (isAliveTeam1 && !isAliveTeam2) {
            return BattleState.Team2Win(team1, team2)
        } else {
            return BattleState.Team1Win(team1, team2)
        }
    }

    fun nextBattleTurn() {
        team1.warriorsList.shuffle()
        team2.warriorsList.shuffle()
        val minRange: Int = if (team1.warriorsList.size > team2.warriorsList.size) {
            team1.warriorsList.size
        } else {
            team2.warriorsList.size
        }
        for (i: Int in 0 until minRange) {
            val turnOfAttack: Boolean = Random.nextBoolean()
            if (turnOfAttack) {
                team1.warriorsList[i].atack(team2.warriorsList[i])
                if (!team2.warriorsList[i].isKilled) {
                    team2.warriorsList[i].atack(team1.warriorsList[i])
                } else {
                    continue
                }
            } else {
                team2.warriorsList[i].atack(team1.warriorsList[i])
                if (!team1.warriorsList[i].isKilled) {
                    team1.warriorsList[i].atack(team2.warriorsList[i])
                } else {
                    continue
                }
            }
        }
        team1.warriorsList.forEach {
            if (it.isKilled) {
                team1.warriorsList.remove(it)
            } else {
            }
        }
        team2.warriorsList.forEach {
            if (it.isKilled) {
                team1.warriorsList.remove(it)
            } else {
            }
        }
    }
}