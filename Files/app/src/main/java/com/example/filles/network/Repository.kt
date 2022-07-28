package com.example.filles.network

import android.app.Activity
import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import java.io.File
import java.util.concurrent.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Repository(private val context: Context) {
    private var downloadID: Long = 0
    private val downloadingLiveEvent = MutableLiveData<Boolean>()
    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    val downloadingLiveData: LiveData<Boolean>
        get() = downloadingLiveEvent

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun save(key: String, value: String) {

        Log.d("save", " start $key $value")
        coroutineScope.launch {
            val deffered = coroutineScope.async {
                sharedPrefs.edit()
                    .putString(key, value)
                    .apply()
                Log.d("saved", "$key $value")
            }
            deffered.await()
            downloadingLiveEvent.postValue(false)
        }


    }

    fun isFirstStart(): Boolean {
        return if (!sharedPrefs.getBoolean(FIRST_START, false)) {
            sharedPrefs.edit()
                .putBoolean(FIRST_START, true)
            true
        } else false

    }

    fun isDownloaded(key: String): Boolean {
        return !sharedPrefs.getString(key, "").isNullOrBlank()
    }

    suspend fun getFile(url: String): ResponseBody {

        return Network.api.getFile(url)

        //https://gitlab.skillbox.ru/pavlenko_nikolai/android_basic/-/blob/master/Files/README.md
        //https://github.com/Kotlin/kotlinx.coroutines/raw/81e17dd37003a7105e542eb725f51ee0dc353354/README.md
    }

    suspend fun downloadManagerDownload(url: String) {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)

if (url.isNotBlank()){
    if(!(downloadUri.scheme.isNullOrBlank() && downloadUri.host.isNullOrBlank())){

            downloadingLiveEvent.postValue(true)
        withContext(Dispatchers.IO) {
            val folder = context.getExternalFilesDir("external_storage/files/")
            var fileName = url.substring(url.lastIndexOf("/") + 1)
            fileName = "${System.currentTimeMillis()}_$fileName"
            val file = File(folder, fileName)

            try {
                val request: DownloadManager.Request = DownloadManager.Request(downloadUri)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(Uri.fromFile(file))
                    .setTitle(fileName)
                    .setDescription("Downloading")
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                downloadID = downloadManager.enqueue(request)
                val query = DownloadManager.Query().setFilterById(downloadID)
                withContext(Dispatchers.IO) {

                    var progress: Int = 0
                    Log.d("Repository", "${downloadingLiveEvent.value}")
                    while (downloadingLiveEvent.value == true) {

                        val cursor = downloadManager.query(query)
                        if (cursor.moveToFirst()) {
                            when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                                DownloadManager.STATUS_FAILED -> {
                                    downloadManager.remove(downloadID)
                                    Log.d("Repository", "failed ${downloadingLiveEvent.value}")
                                    downloadingLiveEvent.postValue(false)
                                }
                                DownloadManager.STATUS_PAUSED -> {
                                    downloadManager.remove(downloadID)
                                    Log.d("Repository", "paused ${downloadingLiveEvent.value}")
                                    downloadingLiveEvent.postValue(false)
                                }
                                DownloadManager.STATUS_PENDING -> {

                                }
                                DownloadManager.STATUS_RUNNING -> {
                                    Log.d("Repository", "running ${downloadingLiveEvent.value}")
                                    val total = cursor.getLong(
                                        cursor
                                            .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                                    )
                                    if (total >= 0) {
                                        val downloaded =
                                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                        progress = (downloaded * 100L / total).toInt()
                                    }
                                }
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    progress = 100
                                    Log.d("Repository", "success ${downloadingLiveEvent.value}")
                                    save(url, fileName)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "Download $fileName Completed",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                            }
                        }
                        cursor.close()
                    }
                }


            } catch (t: Throwable) {

            }
        }
    }
}else{
    withContext(Dispatchers.Main) {
        Toast.makeText(
            context,
            "Enter correct url",
            Toast.LENGTH_SHORT
        )
            .show()
    }
}
    }

    companion object {
        const val SHARED_PREF_NAME = "files_shared_prefs"
        private val FIRST_START = "first_start"
        private const val DATASTORE_NAME = "datastore"
        private val keyList = mutableListOf<Preferences.Key<String>>()
    }
}