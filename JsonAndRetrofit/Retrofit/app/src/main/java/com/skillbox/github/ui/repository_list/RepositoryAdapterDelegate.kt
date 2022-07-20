package com.skillbox.github.ui.repository_list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.network.RemoteRepository
import com.skillbox.github.network.RemoteUser
import com.skillbox.multithreading.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.view.*

class RepositoryAdapterDelegate : AbsListItemAdapterDelegate<RemoteRepository,RemoteRepository,RepositoryAdapterDelegate.Holder>(){


    override fun isForViewType(
        item: RemoteRepository,
        items: MutableList<RemoteRepository>,
        position: Int
    ): Boolean {
        return item is RemoteRepository
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_repo))
    }

    override fun onBindViewHolder(item: RemoteRepository, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class Holder(override val containerView: View) : RecyclerView.ViewHolder(
        containerView
    ),LayoutContainer{
fun bind(repository: RemoteRepository){
    containerView.titleTextView.text = containerView.resources
        .getString(R.string.repo_info,repository.id,repository.name)
}
    }
}
