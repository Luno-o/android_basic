package com.example.a01_generics

import java.util.*

fun main() {
   val list = mutableListOf(10000.1,22000.9,2.67,25.0,845546.0,18.9,24.0,27.0,9223372036854779.0)
    print(evenFilter(list))

}
fun <T:Number> evenFilter(list: List<T>):List<T>{
    val evenList = mutableListOf<T>()
    for (element in list){
        if (element.toLong()%2 == 0L && element.toDouble() - element.toLong()== 0.0 && element.toLong() < Long.MAX_VALUE){
            evenList.add(element)
        }
        else{
            continue
        }
    }
    return evenList
}