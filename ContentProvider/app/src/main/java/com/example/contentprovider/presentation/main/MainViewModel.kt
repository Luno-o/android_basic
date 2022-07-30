package com.example.filles.ui.main

import android.app.Application
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.filles.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class MainViewModel(application: Application) : AndroidViewModel(application) {
private val repository = Repository(application)
 val isDownloading = repository.downloadingLiveData
private val context = application
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
    fun getFileForShare():String{
          return repository.getFileForShare()
    }
     suspend fun getFileWithDownloadManager(url: String){
         if (!repository.isDownloaded(url)){
             if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
        repository.downloadManagerDownload(url)}
             else{
                 Toast.makeText(context, "External Storage not available", Toast.LENGTH_SHORT)
                     .show()
             }
             }
         else{
             Toast.makeText(context, "Already Downloaded", Toast.LENGTH_SHORT)
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