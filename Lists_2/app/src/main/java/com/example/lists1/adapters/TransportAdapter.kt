package com.example.lists1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.lists1.Transport
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TransportAdapter(onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Transport>(TransportDiffUtilCallback()) {



    init {
        delegatesManager.addDelegate(WatercraftAdapterDelegate(onItemClick))
            .addDelegate(AircraftAdapterDelegate(onItemClick))
            .addDelegate(RoadTransportAdapterDelegate(onItemClick))
    }


    class TransportDiffUtilCallback : DiffUtil.ItemCallback<Transport>() {
        override fun areItemsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return when {
                oldItem is Transport.AirCraft && newItem is Transport.AirCraft -> oldItem.id == newItem.id
                oldItem is Transport.WaterCraft && newItem is Transport.WaterCraft -> oldItem.id == newItem.id
                oldItem is Transport.RoadTransport && newItem is Transport.RoadTransport -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem == newItem
        }

    }

}