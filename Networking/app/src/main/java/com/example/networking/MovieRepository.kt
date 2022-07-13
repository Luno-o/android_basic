package com.example.networking

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MovieRepository {
    val MOVIE_API_KEY = "3bb669f3"

    fun search(movieTittle: String,movieYear: Int, movieType:String,callback:(List<Movie>)->Unit){
//http://www.omdbapi.com/?apikey=[yourkey]&s
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("apikey",MOVIE_API_KEY)
            .addQueryParameter("s",movieTittle)
            .build()
val request =Request.Builder()
    .get()
    .url(url)
    .build()

        val client = OkHttpClient()
        Thread{
            try {
val response = client.newCall(request).execute()
            response.isSuccessful
                Log.d("Server","response successful = ${response.isSuccessful}")
            }catch (e: IOException){
Log.d("Server","execute request error = ${e.message}")

            }
            callback(emptyList())
        }.start()
    }

}