package com.example.networking

import android.util.Log
import com.example.networking.network.Network
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {

    fun search(
        movieTittle: String,
        movieYear: String,
        movieType: String,
        callback: (List<Movie>) -> Unit,
        errorCallback: (e: Throwable)-> Unit
    ):Call {
        return Network.getSearchMovieCall(movieTittle,movieYear,movieType).apply {
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
            val movieArray = jsonObject.getJSONArray("Search")
            (0 until movieArray.length()).map { index ->
                movieArray.getJSONObject(index)
            }.map { movieJsonObject ->
                val title = movieJsonObject.getString("Title")
                val year = movieJsonObject.getString("Year")
                val id = movieJsonObject.getString("imdbID")
                val type = movieJsonObject.getString("Type")
                val poster = movieJsonObject.getString("Poster")
                Movie(title,type,year,id,poster)
            }

        } catch (e: JSONException) {
            Log.d("Server", "parse response error = ${e.message}", e)
            emptyList()
        }

    }

}