package com.example.flowexample.network

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.flowexample.db.Database
import com.example.flowexample.db.models.MovieDB
import com.example.flowexample.models.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber


class MovieRepository(val context: Context) {
    private val moviesDao = Database.instance.movieDBDao()

    suspend fun searchMovies(query: String, movieType: MovieType): List<MovieDB> {
        return if (isConnected(context)) {
            val movieList =
                Network.movieApi.searchMovie(query, movieType.name.lowercase())?.search.orEmpty()
            saveMovies(movieList)
            movieList
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show()
            }
            Timber.d("search movies else $query $movieType")
moviesDao.getMoviesByTitleAndType(query, movieType.name).orEmpty()
//            moviesDao.getAllMoviesDB().filter {
//                it.title.contains(query, ignoreCase = true) && it.type == movieType
//            }
        }


    }

    fun observeMovies(): Flow<List<MovieDB>> {
        return moviesDao.observeMovies()
    }

    private suspend fun saveMovies(movies: List<MovieDB>) {
        moviesDao.insertMoviesDB(movies)
    }

    private fun isConnected(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false
        connMgr.allNetworks.forEach { network ->
            connMgr.getNetworkInfo(network)?.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }
        Timber.d("Wifi connected: $isWifiConn")
        Timber.d("Mobile connected: $isMobileConn")
        return isWifiConn
    }
}
