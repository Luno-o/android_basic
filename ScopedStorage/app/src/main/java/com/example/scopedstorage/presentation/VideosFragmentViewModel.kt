package com.example.scopedstorage.presentation

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.utils.haveQ
import com.example.scopedstorage.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class VideosFragmentViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    private val videoRepository = VideoRepository(application)
    private val imageViewModel = MutableLiveData<List<Video>>()
    val imageLiveData: LiveData<List<Video>>
    get()= imageViewModel
    private var pendingDeleteId: Long? = null
    private var pendingDeleteUri: Uri? = null
    private val permissionGrantedMutableLiveData = MutableLiveData<Boolean>()
    val permissionGrantedLiveData: LiveData<Boolean>
    get()= permissionGrantedMutableLiveData
    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()
    val recoverableActionLiveData: LiveData<RemoteAction>
        get()= recoverableActionMutableLiveData

private var isObservingStarted : Boolean = false

     fun getVideo(){
        viewModelScope.launch {
           val  videos =videoRepository.getVideos()
            Log.d("firstFragmentViewModel"," videos $videos")
           imageViewModel.postValue(videos)
        }
    }
    fun permissionGranted(){
        videoRepository.observeVideo { getVideo() }
        permissionGrantedMutableLiveData.postValue(true)
    }
    fun permissionDenied(){
        getVideo()
        if (isObservingStarted.not()){
        videoRepository.unRegisterObserver()
            isObservingStarted = true
        }
        permissionGrantedMutableLiveData.postValue(false)
    }
    fun saveVideo(name: String, url: String){
        viewModelScope.launch {
            videoRepository.saveVideo(name, url)
        }

    }
    fun saveVideo(uri: Uri,url: String){
        viewModelScope.launch {
            videoRepository.saveVideo(uri, url){
                deleteVideo(it)
            }
        }
    }
    private fun deleteVideo(uri: Uri){
        viewModelScope.launch {
            try {
                videoRepository.deleteVideo(uri)
                pendingDeleteUri = null
            }catch (t: Throwable){
                Log.e("VideoFragmentViewModel","exception $t",t)
                if (haveQ() && t is RecoverableSecurityException){
                    pendingDeleteUri = uri
                    recoverableActionMutableLiveData.postValue( t.userAction)
                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(context, "VideoDeleteError", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun deleteVideo(id: Long){
        viewModelScope.launch {
            try {
        videoRepository.deleteVideo(id)
                pendingDeleteId = null
            }catch (t: Throwable){
                Timber.e(t)
                if (haveQ() && t is RecoverableSecurityException){
pendingDeleteId = id
                   recoverableActionMutableLiveData.postValue( t.userAction)
                }else{
                withContext(Dispatchers.Main){
                Toast.makeText(context, "VideoDeleteError", Toast.LENGTH_SHORT).show()
                }
                }
            }
        }
    }
fun confirmDelete(){
    pendingDeleteId?.let {
        deleteVideo(it)
    }
    pendingDeleteUri?.let {
        deleteVideo(it)
    }
}
    fun declineDelete(){
        pendingDeleteId = null
        pendingDeleteUri = null
    }
    override fun onCleared() {
        super.onCleared()
        videoRepository.unRegisterObserver()
    }
}