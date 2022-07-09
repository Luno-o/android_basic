package com.skillbox.multithreading.threading

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.multithreading.networking.Movie

class MovieAdapter() : AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallBack()){


    init {
delegatesManager.addDelegate(MovieAdapterDelegate())
    }

    class MovieDiffUtilCallBack:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return when{
                oldItem is Movie && newItem is Movie-> oldItem.title == newItem.title
                else->false
            }
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}