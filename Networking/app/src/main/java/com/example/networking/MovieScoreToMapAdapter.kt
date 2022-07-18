package com.example.networking

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

class MovieScoreToMapAdapter {
@FromJson
    fun fromJson(remoteMovie: RemoteMovie):Movie{
        val movieMap = mutableMapOf<String,String>()
for (score in remoteMovie.scores){
    movieMap[score.source] = score.value
}
        return Movie(
            title = remoteMovie.title,
            year = remoteMovie.year,
            id = remoteMovie.id,
            type = remoteMovie.type,
            poster = remoteMovie.poster,
            rating = remoteMovie.rating,
            scores = movieMap
        )
    }
    @ToJson
    fun toJson(movie: Movie):RemoteMovie{
    val movieList = mutableListOf<Score>()
    for (keySource in movie.scores){
        movieList.add(Score(keySource.key,keySource.value))
    }
        return RemoteMovie(
            title = movie.title,
            year = movie.year,
            id = movie.id,
            type = movie.type,
            poster = movie.poster,
            rating = movie.rating,
            scores = movieList
        )
    }

    @JsonClass(generateAdapter = true)
    data class RemoteMovie(
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: Int,
        @Json(name = "imdbID")
        val id: String,
        @Json(name = "Type")
        val type: String,
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Rated")
        val rating: MovieRating,
        @Json(name = "Ratings")
        val scores: List<Score>
    )
}