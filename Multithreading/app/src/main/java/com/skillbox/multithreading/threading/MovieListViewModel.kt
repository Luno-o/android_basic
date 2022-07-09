package com.skillbox.multithreading.threading

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network

class MovieListViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData
    private val moviesId = listOf(
        "tt0111161",
        "tt0068646",
        "tt0468569",
        "tt0071562",
        "tt0111161",
        "tt0068646",
        "tt0468569",
        "tt0071562",
        "tt0108052",
        "tt0050083"
    )


    fun requestMovie() {
        Log.d("ThreadTest","requestMovie on ${Thread.currentThread().name}")
           repository.fetchMovie(moviesId){
               Log.d("ThreadTest","requestMovie in callback ${Thread.currentThread().name}")
                val updateList = it + movieLiveData.value.orEmpty()
                movieLiveData.postValue(updateList)
            }
            Log.d("ThreadTest","requestMovie end ${Thread.currentThread().name}")
        }


    fun addMovie(movie: Movie) {
        val updateList = listOf(movie) + movieLiveData.value.orEmpty()
        movieLiveData.postValue(updateList)
    }
}