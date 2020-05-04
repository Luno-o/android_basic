package com.example.kotlinbasic
val arrayList = arrayListOf<Int>()
var countOfPositiveNumbers: Int=0
val mapGCD = mutableMapOf<Int,Int>()

fun main() {
    println("Введите размер массива: ")
    val n = readLine()?.toIntOrNull() ?: return
    buildArrayList(n)
    println(countOfPositiveNumbers)
    var filterArray = arrayList.filter { it%2 == 0 }
    println(filterArray)
    val setCollection = mutableSetOf<Int>()
    setCollection.addAll(arrayList)
    println(setCollection)
    sum()
    for (i in 0 until arrayList.size){
        val currentGCD = gcd(arrayList[i], sum())
        mapGCD.put(arrayList[i],currentGCD)
    }
    for (i in 0 until arrayList.size)
    println("Число ${arrayList[i]}, сумма ${sum()}, НОД ${mapGCD[arrayList[i]]}")
}

fun buildArrayList(n: Int){
    println("Вы ввели $n . Введите $n чисел")
    for (i in 0 until n) {
        var m: Int? = readLine()?.toIntOrNull()
        while (m == null) {
            m = readLine()?.toIntOrNull()

        }
        if (m > 0) countOfPositiveNumbers++
        arrayList.add(m)
    }
    println(arrayList)
}

fun sum(): Int{
    var sumNumber = 0
    for (i in 0 until arrayList.size) {
        sumNumber += arrayList[i]
    }
    return sumNumber
}

tailrec fun gcd(a: Int =0 , b: Int = 0): Int{
return if (b==0){
    a
}
    else{
     gcd(b,a%b)
}
}
