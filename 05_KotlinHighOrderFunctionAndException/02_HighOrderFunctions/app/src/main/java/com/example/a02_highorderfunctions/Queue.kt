package com.example.a02_highorderfunctions

fun main() {
    val ochered = Queue<Int>()
    print("${ochered.enquue(1)}, ${ochered.dequeue()}, ${ochered.dequeue()}")

    ochered.enquue(2)
    ochered.enquue(4)
    ochered.enquue(7)
    ochered.enquue(9)
    ochered.enquue(12)
    val newOchered = ochered.filterQueue{it %2 == 0}
    print(newOchered.list)
}

class Queue<T:Number> {
    var list:MutableList<T> = mutableListOf()

    fun enquue(item:T){
        list.add(item)
    }

    fun dequeue():T?{
        return if (list.isNotEmpty()){list.removeAt(0)}else{ null}
    }
    fun filterQueue(funFilter: (T) -> Boolean):Queue<T>{
        val new = Queue<T>()
        for (element in this.list){
            if (funFilter(element)){
                new.list.add(element)
            }
        }
        return new
    }

}