package com.example.roomdao.presentation.order_list.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contentprovider.utils.inflate
import com.example.roomdao.R
import com.example.roomdao.data.db.models.ProductWithCount
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProductWithCountListDelegationAdapter: AbsListItemAdapterDelegate<ProductWithCount,ProductWithCount,ProductWithCountListDelegationAdapter.Holder>() {

    override fun isForViewType(
        item: ProductWithCount,
        items: MutableList<ProductWithCount>,
        position: Int
    ): Boolean {
       return item is ProductWithCount
    }

    override fun onBindViewHolder(
        item: ProductWithCount,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_order))
    }

    class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer{
        fun bind(productWithCount: ProductWithCount){
            Log.d("Holder"," productWithCount $productWithCount")
            containerView.orderTitleTextView.text = productWithCount.title
            containerView.countTextView.text = productWithCount.count.toString()
            containerView.priceTextView.text = productWithCount.price.toString()

            Glide.with(containerView)
                .load(productWithCount.avatar)
                .placeholder(R.drawable.ic_baseline_ice_skating)
                .into(containerView.orderImageView)

        }
    }

}