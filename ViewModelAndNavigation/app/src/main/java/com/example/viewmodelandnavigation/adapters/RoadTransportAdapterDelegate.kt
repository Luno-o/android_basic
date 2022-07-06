package com.example.viewmodelandnavigation.adapters

import android.view.View
import android.view.ViewGroup
import com.example.viewmodelandnavigation.R
import com.example.viewmodelandnavigation.Transport
import com.example.viewmodelandnavigation.inflate

import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_roadtransport.*

class RoadTransportAdapterDelegate(private val onLongItemClick: (position:Int)->Unit,private val onItemClick: (position: Int) -> Unit):AbsListItemAdapterDelegate<Transport.RoadTransport,Transport,RoadTransportAdapterDelegate.RoadTransportHolder>() {

    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
       return item is Transport.RoadTransport
    }

    override fun onCreateViewHolder(parent: ViewGroup): RoadTransportHolder {
       return RoadTransportHolder(
           parent.inflate(R.layout.item_roadtransport),
           onLongItemClick,
           onItemClick
       )
    }

    override fun onBindViewHolder(
        item: Transport.RoadTransport,
        holder: RoadTransportHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
    class RoadTransportHolder(
        view: View, onLongItemClick: (position: Int) -> Unit,onItemClick: (position: Int) -> Unit
    ) : BaseTransportHolder(view, onLongItemClick,onItemClick) {

        fun bind(transport: Transport.RoadTransport) {
            bindMainInfo(transport.model, transport.imageLink)
            fuelConsumptionTextView.text =
                containerView.resources.getString(
                    R.string.fuelConsumption,
                    transport.fuelConsumption
                )
        }
    }

}