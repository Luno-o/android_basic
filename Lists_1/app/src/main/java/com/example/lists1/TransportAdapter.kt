package com.example.lists1

import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import java.util.jar.Manifest

class TransportAdapter(private val onItemClick: (position: Int) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var transports: List<Transport> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return when (transports[position]) {
            is Transport.AirCraft -> TYPE_AIRCRAFT
            is Transport.WaterCraft -> TYPE_WATERCRAFT
            is Transport.RoadTransport -> TYPE_ROADTRANSPORT
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_AIRCRAFT -> AircraftHolder(parent.inflate(R.layout.item_aircraft), onItemClick)
            TYPE_WATERCRAFT -> WatercraftHolder(parent.inflate(R.layout.item_watercraft), onItemClick)
            TYPE_ROADTRANSPORT -> RoadTransportHolder(parent.inflate(R.layout.item_roadtransport), onItemClick)
            else -> error("Incorrect viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AircraftHolder -> {
                val transport = transports[position].let { it as? Transport.AirCraft }
                        ?: error("Transport at position = $position is not Aircraft")
                holder.bind(transport)
            }
            is WatercraftHolder -> {
                val transport = transports[position].let { it as? Transport.WaterCraft }
                        ?: error("Transport at position = $position is not Watercraft")
                holder.bind(transport)
            }
            is RoadTransportHolder -> {
                val transport = transports[position].let { it as? Transport.RoadTransport }
                        ?: error("Transport at position = $position is not RoadTransport")
                holder.bind(transport)
            }
            else -> {
                error("Incorrect viewHolder $holder")
            }
        }
    }

    override fun getItemCount(): Int = transports.size
    fun updateTransports(newTransports: List<Transport>) {
        transports = newTransports
    }
    abstract class BaseTransportHolder(
        val view: View, onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val TAG = "ImageLink"
        private val transportImageView: ImageView = view.findViewById(R.id.transportImageView)
        private val transportModelTextView: TextView = view.findViewById(R.id.transportModelTextView)
        private val serialNumberTextView: TextView = view.findViewById(R.id.serialNumberTextView)
        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        protected fun bindMainInfo(model: String,
        imageLink: String) {
            transportModelTextView.text = view.resources.getString(R.string.model,model)
            serialNumberTextView.text = view.resources.getString(R.string.serial_number,getRandomString((4..7).random()))

            Log.d(TAG, "Link: $imageLink")
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

    class AircraftHolder(
            view: View, onItemClick: (position: Int) -> Unit)
        : BaseTransportHolder(view, onItemClick) {
        private val flightRangeTextView = view.findViewById<TextView>(R.id.flightRangeTextView)
        fun bind(transport: Transport.AirCraft) {
            bindMainInfo(transport.model,transport.imageLink)
            flightRangeTextView.text = view.resources.getString(R.string.flight_range,transport.flightRange)
        }
    }

    class WatercraftHolder(view: View, onItemClick: (position: Int) -> Unit)
        : BaseTransportHolder(view, onItemClick) {
        private val displacementTextView: TextView = view.findViewById(R.id.displacementTextView)
        fun bind(transport: Transport.WaterCraft) {
            bindMainInfo(transport.model,transport.imageLink)
            displacementTextView.text = view.resources.getString(R.string.displacement,transport.displacement)
        }
    }

    class RoadTransportHolder(
            view: View, onItemClick: (position: Int) -> Unit
    ) : BaseTransportHolder(view, onItemClick) {
        private val fuelConsumptionTextView: TextView = view.findViewById(R.id.fuelConsumptionTextView)
        fun bind(transport: Transport.RoadTransport) {
            bindMainInfo(transport.model,transport.imageLink)
            fuelConsumptionTextView.text = view.resources.getString(R.string.fuelConsumption,transport.fuelConsumption)}
        }



    companion object {
        private const val TYPE_AIRCRAFT = 1
        private const val TYPE_WATERCRAFT = 2
        private const val TYPE_ROADTRANSPORT = 3
    }
}