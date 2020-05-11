package com.example.kotlinoop

interface Warrior {
    var isKilled:Boolean
    var dodgeChance: Int
    fun atack(warrior: Warrior)
    fun takeDamage(damage:Int)
}