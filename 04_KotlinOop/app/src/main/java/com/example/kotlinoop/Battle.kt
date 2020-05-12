package com.example.kotlinoop

import kotlin.random.Random

class Battle(var team1: Team, var team2: Team) {
    var isAliveTeam1:Boolean = true
    var isAliveTeam2:Boolean = true
    var endBattle: Boolean = false
    get() {return !isAliveTeam1 || !isAliveTeam2
    }

    fun getBattleState(): BattleState {
        return if (isAliveTeam1 && isAliveTeam2) {
            var team1Left = team1.warriorsList.size
            var team2Left = team2.warriorsList.size
            for (ind in team1.warriorsList){
                if (ind.isKilled)team1Left--
            }
            if (team1Left == 0){
                isAliveTeam1 = false
                endBattle = true
            }

            for (ind in team2.warriorsList){
                if (ind.isKilled)team2Left--
            }
            if (team2Left==0){
                isAliveTeam2 = false
                endBattle = true
            }

            BattleState.CurrentProgress(team1.sumHealthTeam(),team2.sumHealthTeam(), team1Left,team2Left)

        } else if (!isAliveTeam1 && !isAliveTeam2) {
            BattleState.Draw()

        } else if (!isAliveTeam1 && isAliveTeam2) {
            BattleState.Team2Win()
        } else {
            BattleState.Team1Win()
        }
    }
    fun teamAtack(i:Int) {
        val turnOfAttack: Boolean = Random.nextBoolean()
        if (turnOfAttack) {
            if (!team1.warriorsList[i].isKilled){
                team1.warriorsList[i].atack(team2.warriorsList[i])
            }
            if (!team2.warriorsList[i].isKilled) {
                team2.warriorsList[i].atack(team1.warriorsList[i])

            }
        } else {
            if (!team2.warriorsList[i].isKilled){
                team2.warriorsList[i].atack(team1.warriorsList[i])
            }
                if (!team1.warriorsList[i].isKilled) {
                    team1.warriorsList[i].atack(team2.warriorsList[i])
                }
            }

    }
    fun nextBattleTurn() {
        team1.warriorsList.shuffle()
        team2.warriorsList.shuffle()
        val maxRange: Int = if (team1.warriorsList.size > team2.warriorsList.size) {
            team1.warriorsList.size
        } else {
            team2.warriorsList.size
        }
        for (ind: Int in 0 until maxRange) {
            teamAtack(ind)
        }
    }

}

