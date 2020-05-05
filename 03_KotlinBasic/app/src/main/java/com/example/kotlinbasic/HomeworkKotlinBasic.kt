package com.example.kotlinbasic


fun main() {
    println("Введите размер массива: ")
    val n = readLine()?.toIntOrNull() ?: return
    val arrayList = buildArrayList(n)
    println(arrayList)
    var countOfPositiveNumbers: Int = 0
    for (i in arrayList){
        if (i>0){
            countOfPositiveNumbers++
        }
    }
    println(countOfPositiveNumbers)
    var filterArray = arrayList.filter { it%2 == 0 }
    println(filterArray)
    val setCollection = mutableSetOf<Int>()
    setCollection.addAll(arrayList)
    println(setCollection)
    sum(arrayList)
    val mapGCD = mutableMapOf<Int,Int>()
    for (i in 0 until arrayList.size){
        val currentGCD = gcd(arrayList[i], sum(arrayList))
        mapGCD.put(arrayList[i],currentGCD)
    }
    for (key in arrayList) {
        println("Число $key, сумма ${sum(arrayList)}, НОД ${mapGCD[key]}")
    }
}

fun buildArrayList(n: Int):MutableList<Int>{
    println("Вы ввели $n . Введите $n чисел")
    val arrayList = mutableListOf<Int>()
        while (arrayList.size < n) {
           arrayList.add(readLine()?.toIntOrNull()?:continue)
    }

    return arrayList
}

fun sum(list: MutableList<Int>):Int{
    var sumNumber = 0
    for (i in 0 until list.size) {
        sumNumber += list[i]
    }
    return sumNumber
}

tailrec fun gcd(a: Int =0 , b: Int = 0): Int{
return if (b==0){
    a
}
    else{
     gcd(b,Math.abs(a%b))
}
}
