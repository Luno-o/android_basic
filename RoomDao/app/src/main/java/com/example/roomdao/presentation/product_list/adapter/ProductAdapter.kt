package com.example.roomdao.presentation.product_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.roomdao.data.db.models.Product
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductAdapter(onItemClick: (product: Product)->Unit): AsyncListDifferDelegationAdapter<Product>(ProductDiffUtillCallback()) {
    init {
        delegatesManager.addDelegate(ProductListDelegationAdapter(onItemClick))
    }
    class ProductDiffUtillCallback: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return when {
                oldItem is Product && newItem is Product -> oldItem.id == newItem.id
                else -> false
            }
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return     oldItem == newItem
        }

    }
}