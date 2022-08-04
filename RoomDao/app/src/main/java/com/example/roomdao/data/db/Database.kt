package com.example.roomdao.data.db

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: SkillBoxDatabase
    private set
    fun init(context: Context){
     instance = Room.databaseBuilder(context,
        SkillBoxDatabase::class.java,
     SkillBoxDatabase.DB_NAME)
         .build()
    }
}