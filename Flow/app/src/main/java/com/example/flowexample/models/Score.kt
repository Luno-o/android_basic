package com.example.flowexample.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Score(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)
