package com.example.flowexample.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.flowexample.models.MovieType
import com.example.flowexample.network.MovieRepository
import com.example.flowexample.db.models.MovieDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)
    private var currentJob: Job? = null
    private val moviesLiveData = MutableLiveData<List<MovieDB>>()
    val moviesLD: LiveData<List<MovieDB>>
        get() = moviesLiveData

    private val progressLiveData = MutableLiveData<Boolean>()
    val progressLD:LiveData<Boolean>
        get() = progressLiveData

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun bind(queryFlow: Flow<String>, movieType: Flow<MovieType>) {
Timber.d("bind viewModel start")
        currentJob = combine(queryFlow, movieType) { query, typeMovie ->
            query to typeMovie
        }.onEach { progressLiveData.postValue(true)
            Timber.d("query = $it") }
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                   Timber.d("start search $it")
               }
            .mapLatest { (query, typeMovie) -> repository.searchMovies(query, typeMovie) }
            .onEach {
                progressLiveData.postValue(false)
                moviesLiveData.postValue(it) }

            .launchIn(viewModelScope)

    }
     fun observeMovies(){
        viewModelScope.launch{
        repository.observeMovies().collect{
            moviesLiveData.postValue(it)
        }
        }
    }

   private fun cancelJob() {
        currentJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}