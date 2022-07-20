package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.network.RemoteRepository
import okhttp3.Call


class RepositoryViewModel: ViewModel() {
    private val repository = RepoRepository()
    private val repoLiveData = MutableLiveData<List<RemoteRepository>>()

    val repos : LiveData<List<RemoteRepository>>
    get() = repoLiveData

    fun searchRepo(){
        repository.getRepo({
            repoLiveData.postValue(it)
        },{

        })
    }
}