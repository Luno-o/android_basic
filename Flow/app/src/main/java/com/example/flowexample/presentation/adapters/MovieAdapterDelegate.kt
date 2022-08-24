package com.example.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowexample.R
import com.example.flowexample.db.models.MovieDB
import com.example.flowexample.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieAdapterDelegate :
    AbsListItemAdapterDelegate<MovieDB, MovieDB, MovieAdapterDelegate.MovieHolder>() {


    override fun isForViewType(item: MovieDB, items: MutableList<MovieDB>, position: Int): Boolean {
        return item is MovieDB
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(item: MovieDB, holder: MovieHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class MovieHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(movie: MovieDB) {
//    var scoreString = StringBuilder()
//    for (score in movie.scores){
//        scoreString = scoreString.append(""+score.key+": " + score.value).append("\n")
//    }
            val year: String = movie.year
            containerView.titleTextView.text = containerView.context.resources.getString(
                R.string.movie_info,
                movie.title, movie.id, year, movie.type, "", ""
            )
            Glide.with(containerView)
                .load(movie.poster)
                .into(containerView.posterImageView)

        }
    }
}