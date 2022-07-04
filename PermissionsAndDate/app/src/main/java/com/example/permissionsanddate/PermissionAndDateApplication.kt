package com.example.permissionsanddate

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class PermissionAndDateApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}