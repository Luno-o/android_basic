package com.example.networking

import android.util.Log
import com.example.networking.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {

    fun search(
        movieTittle: String,
        callback: (List<Movie>) -> Unit,
        errorCallback: (e: Throwable)-> Unit
    ):Call {
        return Network.getSearchMovieCall(movieTittle).apply {
        enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Server", "execute request error = ${e.message}")
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                val responseString = response.body?.string().orEmpty()
                Log.d("Server", "response = $responseString")
                val movies = parseMovieResponse(responseString)
                callback(movies)
                } else{
                //    errorCallback(response.message)
                }
            }

        })
        }

    }

    private fun parseMovieResponse(responseBodyString: String): List<Movie> {
        return try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArray = jsonObject.getJSONArray("Search").toString()
           val moshi = Moshi.Builder().build()
            val movieListType = Types.newParameterizedType(List::class.java,Movie::class.java)
            val adapter = moshi.adapter<List<Movie>>(movieListType).nonNull()
            Log.d("Server", "parse response error = ${movieArray}")
            adapter.fromJson(movieArray)!!
        } catch (e: JSONException) {
            Log.d("Server", "parse response error = ${e.message}", e)
            emptyList()
        }

    }

}