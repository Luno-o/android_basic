package com.example.scopedstorage.presentation

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context

import android.provider.MediaStore
import com.example.scopedstorage.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(private val context: Context) {


    suspend fun getImage():List<Image>{
        val images = mutableListOf<Image>()
withContext(Dispatchers.IO){
    context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        null
    )?.use {cursor->
        while (cursor.moveToNext()){
          val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
          val name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
          val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))

            val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id)
            images+= Image(id, uri, name, size)
        }
    }
}

        return images
    }
}