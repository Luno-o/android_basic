package com.example.flowexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flowexample.db.models.MovieDB

@Database(entities = [MovieDB::class], version =MovieDatabase.DB_VERSION )
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDBDao(): MovieDBDao
    companion object{
        const val DB_VERSION = 1
        const val DB_NAME = "movie_DB"
    }
}