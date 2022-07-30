package com.example.contentprovider

import android.app.Application
import android.os.StrictMode
import android.util.Log

class ContentProviderApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("ContentProvider","running")
        if (BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskWrites()
                    .detectDiskReads()
                    .detectNetwork()
                    .penaltyDeath()
                    .build()
            )
        }
    }
}