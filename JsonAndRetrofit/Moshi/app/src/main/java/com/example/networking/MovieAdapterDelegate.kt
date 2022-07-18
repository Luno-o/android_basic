package com.example.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.multithreading.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapterDelegate : AbsListItemAdapterDelegate<Movie,Movie,MovieAdapterDelegate.MovieHolder>(){


    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return item is Movie
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(item: Movie, holder: MovieHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class MovieHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
fun bind(movie: Movie){
    var scoreString = StringBuilder()
    for (score in movie.scores){
        scoreString = scoreString.append(""+score.key+": " + score.value).append("\n")
    }
containerView.titleTextView.text = containerView.context.resources.getString(R.string.movie_info,
    movie.title,movie.id,movie.year,movie.type,movie.rating.name,scoreString)
    Glide.with(containerView)
        .load(movie.poster)
        .into(containerView.posterImageView)

}
    }
}