package com.example.a01_generics

import java.util.*

fun main() {

}
fun <T:Number> evenFilter(list: List<T>):List<T>{
    val evenList = mutableListOf<T>()
    for (element in list){
        if (element.toInt()%2 == 0){
            evenList.add(element)
        }
        else{
            continue
        }
    }
    return evenList
}