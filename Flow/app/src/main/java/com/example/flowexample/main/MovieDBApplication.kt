package com.example.flowexample.main

import android.app.Application
import com.example.flowexample.db.Database
import timber.log.Timber

class MovieDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}