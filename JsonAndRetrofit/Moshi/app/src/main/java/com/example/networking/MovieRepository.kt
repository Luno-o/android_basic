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
        callback: (Movie) -> Unit,
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
                val movie = parseMovieResponse(responseString)
                callback(movie)
                } else{
                //    errorCallback(response.message)
                }
            }

        })
        }

    }

    private fun parseMovieResponse(responseBodyString: String): Movie {
        return try {
            val jsonObject = JSONObject(responseBodyString)
            val string:String =jsonObject.getString("Response")
            if (string == "False"){
                Movie("",0,"","","",MovieRating.PG, mutableMapOf())
            }
            else{
           val moshi = Moshi.Builder()
               .add(MovieScoreToMapAdapter())
               .build()
            val adapter = moshi.adapter(Movie::class.java).nonNull()
            adapter.fromJson(responseBodyString)!!
            }
        } catch (e: JSONException) {
            Log.d("Server", "parse response error = ${e.message}", e)
            Movie("",0,"","","",MovieRating.PG, mutableMapOf())
        }

    }

}