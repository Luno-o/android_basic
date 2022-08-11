package com.example.scopedstorage.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contentprovider.utils.inflate
import com.example.scopedstorage.Video
import com.example.scopedstorage.R
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.view.*

class VideoDelegationAdapter(private val onClick:(id:Long)->Unit): AbsListItemAdapterDelegate<Video,Video,VideoDelegationAdapter.Holder>() {



    override fun isForViewType(item: Video, items: MutableList<Video>, position: Int): Boolean {
        return item is Video
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_image),onClick)
    }

    override fun onBindViewHolder(item: Video, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class Holder(override val containerView: View,val onClick:(id: Long)->Unit):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(image: Video){
            containerView.deleteImageButton.setOnClickListener {
                onClick(image.id)
            }
            Glide.with(containerView)
                .load(image.uri)
                .into(containerView.itemImageView)
            containerView.nameTextView.text = image.name
            containerView.sizeTextView.text = image.size.toString()
        }
    }


}