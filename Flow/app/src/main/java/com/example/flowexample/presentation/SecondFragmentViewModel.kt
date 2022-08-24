package com.example.flowexample.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.flowexample.db.models.MovieDB
import com.example.flowexample.network.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SecondFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val repository = MovieRepository(application)
    private val moviesLiveData = MutableLiveData<List<MovieDB>>()

    private val _state = MutableStateFlow<List<MovieDB>>(emptyList())
    val state = _state.asStateFlow()
    val moviesLD: LiveData<List<MovieDB>>
        get() = moviesLiveData

    fun observeMovies(){
        viewModelScope.launch{
            repository.observeMovies().collect{
                _state.value = it
            }
        }
    }
}