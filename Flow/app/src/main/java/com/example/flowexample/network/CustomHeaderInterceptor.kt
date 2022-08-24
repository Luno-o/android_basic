package com.example.flowexample.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomHeaderInterceptor (
        ):Interceptor{
    private val MOVIE_API_KEY = "3bb669f3"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("apikey", MOVIE_API_KEY)
            .build()
        val modifiedRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }
}