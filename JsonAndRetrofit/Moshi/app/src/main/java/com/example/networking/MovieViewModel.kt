package com.example.networking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import okhttp3.Call
import java.io.IOException
import java.lang.Exception
import kotlin.random.Random

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val hasMovieLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<IOException>()

    private var currentCall: Call? = null

    val error: LiveData<IOException>
        get() = errorLiveData

    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    val isLoadingData: LiveData<Boolean>
        get() = isLoadingLiveData

    val hasMovieData: LiveData<Boolean>
        get() = hasMovieLiveData

    fun searchMovie(movieTittle: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.search(movieTittle, {
            if (it.title != "") {
                Log.d("Server", "viewmodel if = $it")
                val updateData = listOf(it)
                movieLiveData.postValue(updateData)
                isLoadingLiveData.postValue(false)
            } else {

                isLoadingLiveData.postValue(false)
                Log.d("Server", "viewmodel else = $it")
                hasMovieLiveData.postValue(true)
            }
            Log.d("Server", "response = $it")
            currentCall = null
        }, {
            errorLiveData.postValue(it as IOException)
        })
        movieLiveData.postValue(emptyList())
        isLoadingLiveData.postValue(false)
    }

    fun addRating() {
        val oldMovie = movieLiveData.value?.get(Random.nextInt(movieLiveData.value.orEmpty().size))
        if (oldMovie != null) {
            val scoreMap = mutableMapOf<String, String>()
            for (key in oldMovie.scores.keys) {
                scoreMap[key] = Random.nextInt(10).toString()
            }
            val newMovie = Movie(
                oldMovie.title,
                oldMovie.year,
                oldMovie.id,
                oldMovie.type,
                oldMovie.poster,
                oldMovie.rating,
                scoreMap
            )
            val updateData = listOf(newMovie)
            movieLiveData.postValue(updateData)

            val moshi = Moshi.Builder()
                .add(MovieScoreToMapAdapter())
                .build()
            val adapter = moshi.adapter(Movie::class.java)
            try {
                val movieJson = adapter.toJson(newMovie)
                Log.d("Serialize", "movie to Json = $movieJson")
            } catch (
                e: Exception
            ) {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}