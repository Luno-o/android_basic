package com.example.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: Int,
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Rated")
    val rating: MovieRating,
   @Json(name = "Ratings")
    val scores: MutableMap<String,String>
)