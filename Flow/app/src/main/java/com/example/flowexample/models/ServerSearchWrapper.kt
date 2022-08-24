package com.example.flowexample.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerSearchWrapper<T>(
    @Json(name = "Search")
    val search: List<T>?
) {
}