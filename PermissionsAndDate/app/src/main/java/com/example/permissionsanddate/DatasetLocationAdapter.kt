package com.example.permissionsanddate

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DatasetLocationAdapter (onItemClick: (position: Int)->Unit):
        AsyncListDifferDelegationAdapter<DatasetLocation>(LocationDiffUtilCallback()){


            init {
                delegatesManager.addDelegate(LocationInfoAdapterDelegate(onItemClick))
            }
            class LocationDiffUtilCallback: DiffUtil.ItemCallback<DatasetLocation>() {
                override fun areItemsTheSame(
                    oldItem: DatasetLocation,
                    newItem: DatasetLocation
                ): Boolean {
                    return when{
                        oldItem is DatasetLocation && newItem is DatasetLocation-> oldItem.id == newItem.id
                        else->false
                    }
                }

                override fun areContentsTheSame(
                    oldItem: DatasetLocation,
                    newItem: DatasetLocation
                ): Boolean {
                    return oldItem == newItem
                }
            }
}