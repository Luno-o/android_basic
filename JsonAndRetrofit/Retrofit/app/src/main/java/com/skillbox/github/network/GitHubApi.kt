package com.skillbox.github.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("/user")
    fun searchUser(
    ):Call<RemoteUser>

    @GET("/repositories")
    fun searchRepo(): Call<List<RemoteRepository>>
}