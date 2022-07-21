package com.skillbox.github.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class CustomHeaderInterceptor(

): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.d("accessToken", "interceptor = ${Network.getToken()}")
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", Network.getToken())
            .build()
        return chain.proceed(modifiedRequest)
    }
}