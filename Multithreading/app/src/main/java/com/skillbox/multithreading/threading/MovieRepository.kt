package com.skillbox.multithreading.threading

import android.os.Process
import android.util.Log
import com.bumptech.glide.util.Util
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import java.util.*
import java.util.concurrent.*
import kotlin.concurrent.thread

class MovieRepository {
    private val coreAmount= Runtime.getRuntime().availableProcessors()
    private val taskQueue = LinkedBlockingDeque<Runnable>()
    private val executorService = ThreadPoolExecutor(coreAmount,10,0,TimeUnit.SECONDS,taskQueue,BackgroundThreadFactory)
    private fun getMovieById(movieId: String): Movie? {
        return Network.getMovieById(movieId)
    }

    fun fetchMovie(movieId: List<String>, onMovieFetched: (movies: List<Movie>) -> Unit) {
        val movies = Collections.synchronizedList(mutableListOf<Movie>())
        movieId.forEach {
            executorService.execute {
                if (getMovieById(it) != null) {
                    Log.d("ThreadTest", "fetch on ${Thread.currentThread().name}")
                    val movie: Movie = this.getMovieById(it) as Movie
                    movies.add(movie)
                }
            }
        }
        executorService.execute {
            while (executorService.activeCount > 1) {
            }
            executorService.shutdown()
            onMovieFetched(movies)
        }
    }
    object BackgroundThreadFactory : ThreadFactory {
        override fun newThread(p0: Runnable?): Thread {
            val thread = Thread(p0)
            thread.name = "Custom thread" + thread.name
            thread.priority = Process.THREAD_PRIORITY_BACKGROUND
            thread.setUncaughtExceptionHandler { thread1, throwable -> Log.e("Error",thread1.name + throwable.message)  }
            return thread
        }

    }
}