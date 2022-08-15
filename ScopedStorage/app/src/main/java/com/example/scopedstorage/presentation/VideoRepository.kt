package com.example.scopedstorage.presentation

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper

import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.example.contentprovider.utils.haveQ
import com.example.scopedstorage.Video
import com.example.scopedstorage.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository(private val context: Context) {
    private var observer: ContentObserver? = null

    fun observeVideo(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true, observer!!
        )
    }

    fun unRegisterObserver() {
        observer?.let {
            context.contentResolver.unregisterContentObserver(
                observer!!
            )
        }
    }

    suspend fun getVideos(): List<Video> {
        val videos = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))

                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    videos += Video(id, uri, name, size)
                }
            }
        }

        return videos
    }

    suspend fun saveVideo(name: String, url: String) {
        withContext(Dispatchers.IO) {
            val videoUri = saveVideoDetails(name)
            if (videoUri != null) {
                val isDownload = downloadVideo(url, videoUri)
                if (isDownload) {
                    makeVideoVisible(videoUri)
                }
            }
        }
    }
suspend fun saveVideo(uri: Uri,url: String,deleteVideoCallback:(uri: Uri)->Unit){
    withContext(Dispatchers.IO) {
        val mime= MimeTypeMap.getSingleton()
        val type = mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        if (type == "mp4"){
            downloadVideo(url, uri)
        Log.d("Save2","$uri")
        }else{
            deleteVideoCallback(uri)
            withContext(Dispatchers.Main){
                Toast.makeText(context, "It's not mp4 file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
    suspend fun deleteVideo(uri: Uri){
        withContext(Dispatchers.IO){

            val srcDoc = DocumentFile.fromSingleUri(context,uri)
            srcDoc?.delete()
//        context.contentResolver.delete(uri,null)
        }
    }
    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            val uri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
            )
            context.contentResolver.delete(uri, null, null)
        }
    }

    private suspend fun saveVideoDetails(name: String): Uri? {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        try {

            val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
            val videoDetails = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, name)
                put(MediaStore.Video.Media.MIME_TYPE, "video/*")
                //    put(MediaStore.Images.Media.RELATIVE_PATH,"")
                if (haveQ()) {
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }
            return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
        } catch (t: Throwable) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "its not video", Toast.LENGTH_SHORT).show()
            }
            return null
        }

    }

    private suspend fun downloadVideo(url: String, uri: Uri): Boolean {
        return if (uri.host != null && uri.scheme != null && !uri.lastPathSegment.isNullOrBlank()) {
            try {
                context.contentResolver.openOutputStream(uri)?.use { outputStrem ->
                    Network.api.getFile(url)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(outputStrem)
                        }
                }
                true
            } catch (t: Throwable) {
                try{
                    deleteVideo(uri.lastPathSegment?.toLong()!!)
                }catch (t: Throwable){
                    deleteVideo(uri)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "problem with download", Toast.LENGTH_SHORT).show()
                }
                false
            }
        } else {
            withContext(Dispatchers.Main) {

                Toast.makeText(context, "incorrect url", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    private fun makeVideoVisible(videoUri: Uri) {
        if (haveQ().not()) return
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        try {
        context.contentResolver.update(videoUri, videoDetails, null, null)
        }catch (t: Throwable){

        }
    }
}