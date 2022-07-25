package com.example.filles.ui.main

import android.app.Application
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filles.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class MainViewModel(application: Application) : AndroidViewModel(application) {
private val repository = Repository(application)

    suspend fun getFile(url: String,context: Context){
        if (!repository.isDownloaded(url)){
        viewModelScope.launch(Dispatchers.IO){
        val folder = context.getExternalFilesDir("external_storage/files/")
        var fileName = url.substring(url.lastIndexOf("/") + 1)
        fileName = "${System.currentTimeMillis()}_$fileName"
        val file = File(folder,fileName)
            try {
            file.outputStream().use {fileOutput->
        repository.getFile(url).byteStream()
            .use {
                it.copyTo(fileOutput)
            }

            }
                save(url,fileName)
            }catch (t: Throwable){
                Log.d("Viewmodel",t.message.orEmpty())
                file.delete()
            }
        }
        }else{
            Toast.makeText(context, "Already Downloaded", Toast.LENGTH_SHORT)
                .show()
        }

    }
     suspend fun getFileWithDownloadManager(fragment: MainFragment,url: String){
         if (!repository.isDownloaded(url)){
        repository.downloadManagerDownload(fragment,url)}
         else{
             Toast.makeText(fragment.context, "Already Downloaded", Toast.LENGTH_SHORT)
                 .show()
         }
    }
    private fun save(key: String, value :String){
        viewModelScope.launch {
            repository.save(key,value)
        }
    }
    fun isFirstStart():Boolean{
        return repository.isFirstStart()
    }
}