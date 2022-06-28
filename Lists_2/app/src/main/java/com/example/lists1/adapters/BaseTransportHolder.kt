package com.example.lists1.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lists1.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_aircraft.*

abstract class BaseTransportHolder(
    final override val containerView: View, onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    init {
        containerView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    protected fun bindMainInfo(
        model: String,
        imageLink: String
    ) {
        transportModelTextView.text = containerView.resources.getString(R.string.model, model)
        serialNumberTextView.text =
            containerView.resources.getString(
                R.string.serial_number,
                getRandomString((4..7).random())
            )

        Glide.with(itemView)
            .load(imageLink)
            .placeholder(R.drawable.ic_transport)
            .into(transportImageView)

    }

    private fun getRandomString(length: Int): String {
        val allowChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowChars.random() }
            .joinToString("")
    }
}