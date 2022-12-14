package com.example.notifications

import android.app.Application
import timber.log.Timber

class NotificationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NotificationChannels.create(this)
    }
}