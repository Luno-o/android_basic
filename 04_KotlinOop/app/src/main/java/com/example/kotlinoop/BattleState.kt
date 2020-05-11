package com.example.kotlinoop

sealed class BattleState(var team1: Team, var team2: Team) {
    class CurrentProgress(team1: Team,team2: Team):BattleState(team1,team2){
        var stayAliveTeam1: Int = team1.warriorsList.size
        var stayAliveTeam2: Int = team2.warriorsList.size
        fun sumHealthTeam2():Int {
            var sumHealth: Int = 0
            for (n in team2.warriorsList){
                sumHealth+=n.currentHealth
            }
            return sumHealth
        }
        fun sumHealthTeam1():Int {
            var sumHealth: Int = 0
            for (n in team1.warriorsList){
                sumHealth+=n.currentHealth
            }
            return sumHealth
        }
        fun printState(){
            println("Суммарное количество жизней в первой команде ${sumHealthTeam1()} , осталось в живых $stayAliveTeam1")
            println("Суммарное количество жизней во второй команде ${sumHealthTeam2()} , осталось в живых $stayAliveTeam2")
        }
        init {
            printState()
        }

    }
    class Team1Win(team1: Team,team2: Team): BattleState(team1,team2){
        private fun printWiner(){
            print("Первая команда победила")
        }
        init {
            printWiner()
        }
    }

    class Team2Win(team1: Team, team2: Team): BattleState(team1,team2){
        private fun printWiner(){
            print("Вторая команда победила")
        }
        init {
            printWiner()
        }
    }

    class Draw(team1: Team,team2: Team):BattleState(team1,team2){
        private fun printDraw(){
            print("Ничья")
        }
        init {
            printDraw()
        }
    }



}