package com.example.a01_generics

import java.util.*

sealed class Result<out T,R>

data class Success<T,R>(val success: T): Result<T,R>()
data class Error<T,R>(val err: R):Result<T,R>()


fun result():Result<Int,String>{
    return Success(0)
}