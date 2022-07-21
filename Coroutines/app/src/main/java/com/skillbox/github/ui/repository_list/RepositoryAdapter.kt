package com.skillbox.github.ui.repository_list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.network.RemoteRepository
import com.skillbox.github.network.RemoteUser

class RepositoryAdapter(onItemClick: (position: Int) -> Unit) : AsyncListDifferDelegationAdapter<RemoteRepository>(RepositoryDiffUtilCallback()){

    init {
        delegatesManager.addDelegate(RepositoryAdapterDelegate(onItemClick))
    }

    class RepositoryDiffUtilCallback: DiffUtil.ItemCallback<RemoteRepository>(){
        override fun areItemsTheSame(
            oldItem: RemoteRepository,
            newItem: RemoteRepository
        ): Boolean {
          return when{
            oldItem is RemoteRepository && newItem is RemoteRepository -> oldItem.id == newItem.id
              else -> false
          }
        }

        override fun areContentsTheSame(
            oldItem: RemoteRepository,
            newItem: RemoteRepository
        ): Boolean {
            return oldItem == newItem
        }

    }
}