package com.example.flowexample.db.models

import androidx.room.TypeConverter
import com.example.flowexample.models.MovieRating
import com.example.flowexample.models.MovieType


class MovieTypeConverter {
    @TypeConverter
    fun convertMovieTypeToString(movieType: MovieType):String = movieType.name

    @TypeConverter
    fun convertStringToMovieType(string: String): MovieType = MovieType.valueOf(string)

}