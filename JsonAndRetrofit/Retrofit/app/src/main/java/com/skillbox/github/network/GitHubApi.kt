package com.skillbox.github.network

import retrofit2.Call
import retrofit2.http.*

interface GitHubApi {
    @GET("/user")
    fun searchUser(
    ):Call<RemoteUser>

    @GET("/repositories")
    fun searchRepo(): Call<List<RemoteRepository>>

    @GET("/user/starred/{owner}/{repo}")
    fun isStarred(
        @Path("owner")ownerName : String,
        @Path("repo")repositoryName: String,
    ): Call<Boolean>

    @PUT("/user/starred/{owner}/{repo}")
    fun putStar(
        @Path("owner")ownerName : String,
        @Path("repo")repositoryName: String,
    ): Call<Unit>

    @DELETE("/user/starred/{owner}/{repo}")
    fun unStar(
        @Path("owner")ownerName : String,
        @Path("repo")repositoryName: String,
    ): Call<Unit>

}