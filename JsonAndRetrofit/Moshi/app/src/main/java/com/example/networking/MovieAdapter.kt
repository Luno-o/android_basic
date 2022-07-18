package com.example.networking

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieAdapter : AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtillCallback()){
init{
    delegatesManager.addDelegate(MovieAdapterDelegate())
}
    class MovieDiffUtillCallback: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return when{
                oldItem is Movie && newItem is Movie -> oldItem.id == newItem.id
else->false
            }
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}