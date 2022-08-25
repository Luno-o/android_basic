package com.example.backgroundwork

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.BatteryManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.backgroundwork.network.Network
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context,params){
    //надо ли занулять контекст?
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        val uriToDownload = inputData.getString(DOWNLOAD_URI_KEY)?.toUri()
        Timber.d("download started")

        return suspendCoroutine {continuation->
        if (urlToDownload != null && uriToDownload != null) {
            coroutineScope.launch {
 val deferredResult = coroutineScope.async {
                downloadFile(urlToDownload,uriToDownload)
 }
             val result = deferredResult.await()
                continuation.resume(result)
            }
        }else{
            Timber.d("url $urlToDownload uri $uriToDownload")
            try {
            coroutineScope.launch {
                continuation.resume(Result.failure())
                deleteFile(uriToDownload!!) }
            }catch (t:Throwable){
                continuation.resume(Result.failure())
            }
        }
        }
    }


    private suspend fun downloadFile(url: String, uri: Uri): Result {
        return if (uri.host != null && uri.scheme != null && !uri.lastPathSegment.isNullOrBlank()) {
            Timber.d("$uri $url")
            try {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    Network.api.getFile(url)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                }
                Result.success()
            } catch (t: NetworkErrorException){
                Result.retry()
            }
            catch (t: Throwable) {
                    deleteFile(uri)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "problem with download", Toast.LENGTH_SHORT).show()
                }
                Result.failure()
            }
        } else {
            deleteFile(uri)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "incorrect url", Toast.LENGTH_SHORT).show()
            }
            Result.failure()
        }
    }
    private suspend fun deleteFile(uri: Uri){
        withContext(Dispatchers.IO){

            val srcDoc = DocumentFile.fromSingleUri(context,uri)
            srcDoc?.delete()
//        context.contentResolver.delete(uri,null)
        }
    }
    companion object{
       const val DOWNLOAD_URL_KEY = "download url"
       const val DOWNLOAD_URI_KEY = "download uri"
    }
}