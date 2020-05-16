package com.example.a01_generics

import java.util.*
import kotlin.math.round

fun main() {
   val list = mutableListOf(10000.1,22000.9,2.67,25.0,845546.0,18.9,24.0,27.0,9223372036854779.0)

}
fun <T:Number> evenFilterList(list: List<T>):List<T>{
    val evenList = mutableListOf<T>()
    for (element in list){
        if(element is Long && (element == Long.MAX_VALUE)){
            continue
        }
        val long = element.toLong()
        if (long < Long.MAX_VALUE){
            if(round(long.toDouble()) == long.toDouble()&& long%2 == 0L){
                evenList.add(element)
            }

        }
        else{
            evenList.add(element)
        }
    }
    return evenList
}