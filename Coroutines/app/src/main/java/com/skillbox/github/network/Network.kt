package com.skillbox.github.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    var accessToken = "token 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"
    private val okHttpClient = OkHttpClient.Builder()
        .apply {
                Log.d("accessToken", "response = ${getToken()}")
                addNetworkInterceptor(CustomHeaderInterceptor())
        }
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()


    fun getToken():String{
        return accessToken
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
    val gitHubApi: GitHubApi
        get() = retrofit.create()
}