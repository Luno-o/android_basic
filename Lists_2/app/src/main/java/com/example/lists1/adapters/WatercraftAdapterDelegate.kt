package com.example.lists1.adapters

import android.view.View
import android.view.ViewGroup
import com.example.lists1.R
import com.example.lists1.Transport
import com.example.lists1.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_watercraft.*

class WatercraftAdapterDelegate(private val onItemClick: (position: Int) -> Unit)
    : AbsListItemAdapterDelegate<Transport.WaterCraft,Transport,WatercraftAdapterDelegate.WatercraftHolder>() {

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
    class WatercraftHolder(view: View, onItemClick: (position: Int) -> Unit) :
        BaseTransportHolder(view, onItemClick) {

        fun bind(transport: Transport.WaterCraft) {
            bindMainInfo(transport.model, transport.imageLink)
            displacementTextView.text =
                containerView.resources.getString(R.string.displacement, transport.displacement)
        }
    }

}