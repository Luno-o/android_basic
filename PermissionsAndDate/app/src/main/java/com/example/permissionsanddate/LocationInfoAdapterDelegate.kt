package com.example.permissionsanddate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class LocationInfoAdapterDelegate(private val onItemClick: (position: Int)->Unit)
    :AbsListItemAdapterDelegate<DatasetLocation,DatasetLocation,LocationInfoAdapterDelegate.Holder>() {


    override fun isForViewType(
        item: DatasetLocation,
        items: MutableList<DatasetLocation>,
        position: Int
    ): Boolean {
       return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_location,false),onItemClick)
    }

    override fun onBindViewHolder(
        item: DatasetLocation,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
       holder.bind(item.location.latitude.toString(),item.location.longitude.toString(),
           item.location.altitude.toString(),item.location.accuracy.toString(),
           item.location.speed.toString(),item.timeStamp,item.imageLink)
    }
    class Holder(override val containerView: View,onItemClick: (position: Int) -> Unit)
        :RecyclerView.ViewHolder(containerView),LayoutContainer
    {

private val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
    .withZone(ZoneId.systemDefault())
    init {
        containerView.setOnClickListener {
            onItemClick(absoluteAdapterPosition)
        }
    }
    fun bind(
        lat: String,
        lng: String,
        ald: String,
        accuracy:String,
        speed: String,
    timeStamp: Instant,
        imageLink:String
    ){

Glide.with(itemView)
    .load(imageLink)
    .placeholder(R.drawable.ic_launcher_background)
    .into(imageView)

        timestampTextView.text =formatter.format(timeStamp)

        locationInfoTextView.text = containerView.resources.getString(R.string.location_info,lat,lng,ald,accuracy,speed)
    }
    }


}