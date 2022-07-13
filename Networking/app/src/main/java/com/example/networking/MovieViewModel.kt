package com.example.networking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call
import java.io.IOException

class MovieViewModel: ViewModel() {
    private val repository = MovieRepository()
    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<IOException>()

    private var currentCall : Call? = null

    val error: LiveData<IOException>
        get() = errorLiveData

    val movies: LiveData<List<Movie>>
    get() = movieLiveData

    val isLoadingData:LiveData<Boolean>
    get() = isLoadingLiveData

    fun searchMovie(movieTittle: String,movieYear: String = "", movieType:String = ""){
    isLoadingLiveData.postValue(true)
currentCall = repository.search(movieTittle,movieYear,movieType,{
    movieLiveData.postValue(it)
    Log.d("Server", "response = $it")
    isLoadingLiveData.postValue(false)
    currentCall = null
},{
    errorLiveData.postValue(it as IOException)})
        movieLiveData.postValue(emptyList())
        isLoadingLiveData.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
currentCall?.cancel()
    }
}