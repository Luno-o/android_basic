package com.example.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapterDelegate : AbsListItemAdapterDelegate<Movie,Movie,MovieAdapterDelegate.MovieHolder>(){


    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return item is Movie
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(item: Movie, holder: MovieHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class MovieHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
fun bind(movie: Movie){
containerView.titleTextView.text = movie.tittle
}
    }
}