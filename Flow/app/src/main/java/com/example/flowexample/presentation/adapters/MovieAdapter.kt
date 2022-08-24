package com.example.networking

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.flowexample.db.models.MovieDB
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieAdapter : AsyncListDifferDelegationAdapter<MovieDB>(MovieDiffUtillCallback()) {
    init {
        delegatesManager.addDelegate(MovieAdapterDelegate())
    }

    class MovieDiffUtillCallback : DiffUtil.ItemCallback<MovieDB>() {
        override fun areItemsTheSame(oldItem: MovieDB, newItem: MovieDB): Boolean {
            return when {
                oldItem is MovieDB && newItem is MovieDB -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: MovieDB, newItem: MovieDB): Boolean {
            return oldItem == newItem
        }

    }
}