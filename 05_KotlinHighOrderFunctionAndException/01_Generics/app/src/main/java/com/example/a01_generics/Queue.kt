package com.example.a01_generics

fun main() {

}
class Queue<T> {
    private var list:MutableList<T> = mutableListOf()

     fun enquue(item:T){
        list.add(item)
    }

    fun dequeue():T?{
        return list[0]
    }
}


