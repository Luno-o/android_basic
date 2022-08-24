package com.example.flowexample.db

import androidx.room.*
import com.example.flowexample.db.models.MovieDB
import com.example.flowexample.db.models.MovieDbContracts
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesDB(movies: List<MovieDB>)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
    suspend fun getAllMoviesDB(): List<MovieDB>

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.TITLE} LIKE '%'|| :title ||'%' AND ${MovieDbContracts.Columns.TYPE}= :type")
    suspend fun getMoviesByTitleAndType(title: String, type: String): List<MovieDB>

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
    fun observeMovies(): Flow<List<MovieDB>>

    @Delete
    suspend fun removeMovie(movieDB: MovieDB)

    @Update
    suspend fun updateMovieDB(movieDB: MovieDB)
}