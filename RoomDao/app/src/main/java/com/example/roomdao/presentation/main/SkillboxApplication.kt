package com.example.roomdao.presentation.main

import android.app.Application
import com.example.roomdao.data.db.Database

class SkillboxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}