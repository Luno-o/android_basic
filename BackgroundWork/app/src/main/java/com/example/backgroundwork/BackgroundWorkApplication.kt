package com.example.backgroundwork

import android.app.Application
import com.example.backgroundwork.other.NotificationChannels
import timber.log.Timber

class BackgroundWorkApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
        Timber.plant(Timber.DebugTree())
    }
}