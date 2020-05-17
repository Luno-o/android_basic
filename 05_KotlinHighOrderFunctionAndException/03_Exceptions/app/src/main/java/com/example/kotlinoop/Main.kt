package com.example.kotlinoop

fun main() {
    println("Введите количество человек в первой команде:  ")
    var team1 = Team()
    println("Введите количество человек во второй команде:  ")
    val team2 = Team()
    val greatBattle = Battle(team1, team2)
    while (!greatBattle.endBattle) {
        greatBattle.nextBattleTurn()
        greatBattle.getBattleState()
    }
    greatBattle.getBattleState()
}