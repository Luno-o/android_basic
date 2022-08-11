package com.example.scopedstorage.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.scopedstorage.Video
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class VideoAdapter(onClick: (id:Long)->Unit):AsyncListDifferDelegationAdapter<Video>(DiffUtilCallback()) {

init {
    delegatesManager.addDelegate(VideoDelegationAdapter(onClick))
}

class DiffUtilCallback(): DiffUtil.ItemCallback<Video>(){
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return when {
            oldItem is Video && newItem is Video ->
                oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return     oldItem == newItem
    }

}

}