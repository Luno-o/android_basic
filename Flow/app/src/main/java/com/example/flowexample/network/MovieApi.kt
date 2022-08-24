package com.example.flowexample.network

import com.example.flowexample.models.ServerSearchWrapper
import com.example.flowexample.db.models.MovieDB
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun searchMovie(
        @Query("s")
        movieTitle: String,
        @Query("type") movieType: String
    ): ServerSearchWrapper<MovieDB>?
}