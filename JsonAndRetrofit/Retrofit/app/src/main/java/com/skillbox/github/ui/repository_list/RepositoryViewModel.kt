package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.network.RemoteRepository
import okhttp3.Call


class RepositoryViewModel: ViewModel() {
    private val repository = RepoRepository()
    private val repoLiveData = MutableLiveData<List<RemoteRepository>>()
    private val repoStar = MutableLiveData<Boolean>()
    val star: LiveData<Boolean>
    get() = repoStar
    val repos : LiveData<List<RemoteRepository>>
    get() = repoLiveData

    fun searchRepo(){
        repository.getRepo({
            repoLiveData.postValue(it)
        },{

        })
    }
    fun checkStarred(ownerName:String,repoName:String){
        repository.checkStarred(ownerName,repoName,{
            Log.d("Callback"," $it")
            repoStar.postValue(it)
        },{})

    }
    fun setStar(ownerName:String,repoName:String){
        repository.putStar(ownerName,repoName,{
            checkStarred(ownerName,repoName)
        },{})
    }
    fun unStar(ownerName:String,repoName:String){
        repository.unStar(ownerName,repoName,{
            checkStarred(ownerName,repoName)
        },{})
    }
}