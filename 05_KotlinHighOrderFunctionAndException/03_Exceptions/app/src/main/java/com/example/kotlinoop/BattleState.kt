package com.example.kotlinoop

sealed class BattleState {
    class CurrentProgress(var team1Health: Int,var team2Health: Int,var stayAliveTeam1: Int,var stayAliveTeam2: Int) : BattleState() {



        private fun printState() {
            println("Суммарное количество жизней в первой команде $team1Health , осталось в живых $stayAliveTeam1")
            println("Суммарное количество жизней во второй команде $team2Health , осталось в живых $stayAliveTeam2")
        }

        init {
            printState()
        }

    }

    class Team1Win: BattleState() {
        private fun printWiner() {
            println("Первая команда победила")
        }

        init {
            printWiner()
        }
    }

    class Team2Win : BattleState() {
        private fun printWiner() {
            println("Вторая команда победила")
        }

        init {
            printWiner()
        }
    }

    class Draw : BattleState() {
        private fun printDraw() {
            print("Ничья")
        }

        init {
            printDraw()
        }
    }


}