package com.example.flowexample.models

data class User(
    val userId: Long,
    val name: String,
    val age :Int,
    val gender: Gender
) {
}