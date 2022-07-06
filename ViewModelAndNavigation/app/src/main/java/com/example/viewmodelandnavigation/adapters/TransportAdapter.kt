package com.example.viewmodelandnavigation.adapters


import androidx.recyclerview.widget.DiffUtil
import com.example.viewmodelandnavigation.Transport
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TransportAdapter(onLongItemClick:(position:Int)->Unit,onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Transport>(TransportDiffUtilCallback()) {



    init {
        delegatesManager.addDelegate(WatercraftAdapterDelegate(onLongItemClick,onItemClick))
            .addDelegate(AircraftAdapterDelegate(onLongItemClick,onItemClick))
            .addDelegate(RoadTransportAdapterDelegate(onLongItemClick,onItemClick))
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