package com.example.flowexample.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.flowexample.models.MovieRating
import com.example.flowexample.models.MovieType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = MovieDbContracts.TABLE_NAME)
@TypeConverters(MovieTypeConverter::class)
data class MovieDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MovieDbContracts.Columns.IMDB_ID)
    @Json(name = "imdbID")
    val id: String,
    @ColumnInfo(name = MovieDbContracts.Columns.TITLE)
    @Json(name = "Title")
    val title: String,
    @ColumnInfo(name = MovieDbContracts.Columns.YEAR)
    @Json(name = "Year")
    val year: String,
    @ColumnInfo(name = MovieDbContracts.Columns.TYPE)
    @Json(name = "Type")
    val type: MovieType,
    @ColumnInfo(name = MovieDbContracts.Columns.POSTER)
    @Json(name = "Poster")
    val poster: String
)