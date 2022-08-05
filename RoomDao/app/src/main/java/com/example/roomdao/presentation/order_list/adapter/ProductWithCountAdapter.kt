package com.example.roomdao.presentation.order_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.roomdao.data.db.models.OrderWithProducts
import com.example.roomdao.data.db.models.ProductWithCount
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductWithCountAdapter: AsyncListDifferDelegationAdapter<ProductWithCount>(OrderDiffUtillCallback()) {
    init {
        delegatesManager.addDelegate(ProductWithCountListDelegationAdapter())
    }
    class OrderDiffUtillCallback: DiffUtil.ItemCallback<ProductWithCount>(){


        override fun areItemsTheSame(
            oldItem: ProductWithCount,
            newItem: ProductWithCount
        ): Boolean {
            return when {
                oldItem is ProductWithCount && newItem is ProductWithCount ->
                    oldItem.title == newItem.title
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: ProductWithCount,
            newItem: ProductWithCount
        ): Boolean {
            return     oldItem == newItem
        }

    }
}