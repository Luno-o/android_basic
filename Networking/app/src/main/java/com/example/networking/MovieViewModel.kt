package com.example.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    private val repository = MovieRepository()
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
    get() = movieLiveData

    fun searchMovie(movieTittle: String,movieYear: Int = 1900, movieType:String = ""){
repository.search(movieTittle,movieYear,movieType){
val updateList = it + movieLiveData.value.orEmpty()
    movieLiveData.postValue(updateList)
}
    }
}