package com.panko.basicsyntax


val firstName = "Nick"
val lastName = "Pavlenko"
var height = 180
var isChild: Boolean
    get() {
        return weight < 40f || height < 150
    }
    set(value) {}
val weight = 80.2f
var info: String
    get() {
        return "Name: $firstName\nLastname: $lastName\nHeight: " +
                "$height\nWeight: $weight\nIs child: $isChild\n"
    }
    set(value) {}

fun main() {

    print(info)
    height = 140
    print(info)

}