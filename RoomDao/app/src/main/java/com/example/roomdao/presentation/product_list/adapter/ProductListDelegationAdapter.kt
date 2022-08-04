package com.example.roomdao.presentation.product_list.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contentprovider.utils.inflate
import com.example.roomdao.R
import com.example.roomdao.data.db.models.Product
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_order.view.titleTextView
import kotlinx.android.synthetic.main.item_product.view.*
import timber.log.Timber

class ProductListDelegationAdapter: AbsListItemAdapterDelegate<Product,Product,ProductListDelegationAdapter.Holder>() {



    override fun isForViewType(item: Product, items: MutableList<Product>, position: Int): Boolean {
       return item is Product
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_product))
    }

    override fun onBindViewHolder(item: Product, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer{
        fun bind(product: Product){
            Log.d("Holder"," product $product")
            containerView.titleTextView.text = product.title
            containerView.descriptionTextView.text = product.description

            Glide.with(containerView)
                .load(product.avatar)
                .placeholder(R.drawable.ic_baseline_ice_skating)
                .into(containerView.productImageView)
            containerView.addToOrderButton.setOnClickListener {
                //TODO
            }
        }
    }
}