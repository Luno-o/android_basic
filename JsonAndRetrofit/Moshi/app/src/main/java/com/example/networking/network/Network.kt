package com.example.networking.network

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
    private const val MOVIE_API_KEY = "3bb669f3"
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomHeaderInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    fun getSearchMovieCall(movieTitle: String): Call {

        //http://www.omdbapi.com/?apikey=[yourkey]&s
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("t",movieTitle)
            .build()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }
}