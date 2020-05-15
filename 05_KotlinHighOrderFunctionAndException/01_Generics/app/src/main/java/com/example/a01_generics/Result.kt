package com.example.a01_generics

import kotlin.random.Random

fun main(){
    print(result())
}
sealed class Result<out T,R>

data class Success<T,R>(val success: T): Result<T,R>()
data class Error<T,R>(val err: R):Result<T,R>()


fun result():Result<Int,String>{
    return   if (Random.nextBoolean()){
        Success(1)
    }else{
        Error("ошибка")
    }

}