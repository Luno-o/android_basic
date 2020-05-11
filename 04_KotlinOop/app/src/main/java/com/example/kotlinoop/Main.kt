package com.example.kotlinoop

fun main() {
    println("Введите количество человек в первой команде:  ")
    val team1 = Team()
    println("Введите количество человек во второй команде:  ")
    val team2 = Team()
    var greatBattle = Battle(team1, team2)
    while (!greatBattle.endBattle) {
        greatBattle.nextBattleTurn()
        greatBattle.getBattleState()
    }
    greatBattle.getBattleState()
}