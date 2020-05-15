package com.example.a01_generics

fun main() {
val ochered = Queue<Int>()
    print("${ochered.enquue(1)}, ${ochered.dequeue()}, ${ochered.dequeue()}")
}
class Queue<T> {
    private var list:MutableList<T> = mutableListOf()

     fun enquue(item:T){
        list.add(item)
    }

    fun dequeue():T?{
        return if (list.isNotEmpty()){list.removeAt(0)}else{ null}
    }
}


