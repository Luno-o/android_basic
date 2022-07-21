package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.network.RemoteRepository
import kotlinx.coroutines.*
import okhttp3.Call
import java.lang.Exception


class RepositoryViewModel : ViewModel() {
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("Exception", "error ${throwable.message}")
        repoLiveData.postValue(emptyList())
    }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    private val repository = RepoRepository()
    private val repoLiveData = MutableLiveData<List<RemoteRepository>>()
    private val repoStar = MutableLiveData<Boolean>()
    val star: LiveData<Boolean>
        get() = repoStar
    val repos: LiveData<List<RemoteRepository>>
        get() = repoLiveData

    fun searchRepo() {
        coroutineScope.launch {
            val repos = repository.getRepo()
            Log.d("coroutine", " ${Thread.currentThread().name}")
            repoLiveData.postValue(repos)
        }
    }

    private fun alsoCoroutine(){
        coroutineScope.launch {
            Log.d("coroutine", "also ${Thread.currentThread().name}")
        }
    }
    fun checkStarred(ownerName: String, repoName: String) {
        viewModelScope.launch { try {
        val isStarred = repository.checkStarred(ownerName, repoName)
            repoStar.postValue(isStarred)
        }catch (t : Throwable){
            Log.d("Exception", "throw ${t.message}")
        }
        }

    }

    fun setStar(ownerName: String, repoName: String) {
        viewModelScope.launch{
            try {

        repository.putStar(ownerName, repoName)
                checkStarred(ownerName, repoName)
            }catch (e: Exception){
                Log.d("Exception", "throw ${e.message}")
            }
        }
    }

    fun unStar(ownerName: String, repoName: String) {
        viewModelScope.launch {
            try {
        repository.unStar(ownerName, repoName)
                checkStarred(ownerName, repoName)
            }catch (e: Exception){
                Log.d("Exception", "throw ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}