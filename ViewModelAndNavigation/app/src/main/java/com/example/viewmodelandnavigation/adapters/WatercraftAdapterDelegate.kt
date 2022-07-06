package com.example.viewmodelandnavigation.adapters

import android.view.View
import android.view.ViewGroup
import com.example.viewmodelandnavigation.R
import com.example.viewmodelandnavigation.Transport
import com.example.viewmodelandnavigation.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_watercraft.*

class WatercraftAdapterDelegate(private val onLongItemClick:(position:Int)->Unit,private val onItemClick: (position: Int) -> Unit): AbsListItemAdapterDelegate<Transport.WaterCraft,Transport,WatercraftAdapterDelegate.WatercraftHolder>() {

    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
        return item is Transport.WaterCraft
    }

    override fun onCreateViewHolder(parent: ViewGroup): WatercraftHolder {
        return WatercraftHolder(
            parent.inflate(R.layout.item_watercraft),
            onLongItemClick,
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: Transport.WaterCraft,
        holder: WatercraftHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
    class WatercraftHolder(view: View, onLongItemClick: (position: Int) -> Unit,onItemClick: (position: Int) -> Unit) :
        BaseTransportHolder(view, onLongItemClick ,onItemClick,) {

        fun bind(transport: Transport.WaterCraft) {
            bindMainInfo(transport.model, transport.imageLink)
            displacementTextView.text =
                containerView.resources.getString(R.string.displacement, transport.displacement)
        }
    }

}