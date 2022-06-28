package com.example.lists1.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lists1.R
import com.example.lists1.Transport
import com.example.lists1.inflate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_aircraft.*
import kotlinx.android.synthetic.main.item_aircraft.serialNumberTextView
import kotlinx.android.synthetic.main.item_aircraft.transportModelTextView
import kotlinx.android.synthetic.main.item_roadtransport.*
import kotlinx.android.synthetic.main.item_watercraft.*
import kotlinx.android.synthetic.main.item_aircraft.transportImageView as transportImageView1

class TransportAdapter(private val onItemClick: (position: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Transport>(TransportDiffUtilCallback()) {



    init {
        delegatesManager.addDelegate(WatecraftAdapterDelegate(onItemClick))
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