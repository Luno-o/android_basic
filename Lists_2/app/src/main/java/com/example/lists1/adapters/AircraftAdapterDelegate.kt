package com.example.lists1.adapters

import android.view.View
import android.view.ViewGroup
import com.example.lists1.R
import com.example.lists1.Transport
import com.example.lists1.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_aircraft.*

class AircraftAdapterDelegate(private val onItemClick: (position: Int) -> Unit): AbsListItemAdapterDelegate<Transport.AirCraft, Transport,AircraftAdapterDelegate.AircraftHolder>() {


    override fun isForViewType(
        item: Transport,
        items: MutableList<Transport>,
        position: Int
    ): Boolean {
        return item is Transport.AirCraft
    }

    override fun onCreateViewHolder(parent: ViewGroup): AircraftHolder {
       return AircraftHolder(parent.inflate(R.layout.item_aircraft), onItemClick)
    }

    override fun onBindViewHolder(
        item: Transport.AirCraft,
        holder: AircraftHolder,
        payloads: MutableList<Any>
    ) {
       holder.bind(item)
    }
    class AircraftHolder(
        view: View, onItemClick: (position: Int) -> Unit
    ) : BaseTransportHolder(view, onItemClick) {

        fun bind(transport: Transport.AirCraft) {
            bindMainInfo(transport.model, transport.imageLink)
            flightRangeTextView.text =
                containerView.resources.getString(R.string.flight_range, transport.flightRange)
        }
    }

}