package com.example.viewmodelandnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {
private val args:DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (val transport = args.transport){
            is Transport.AirCraft-> {
                view.let { Glide.with(it).load(transport.imageLink).into(transportDetailImageView) }
                transportDetailModelTextView.text = view.resources.getString(R.string.model, transport.model)
                flightRangeDetailTextView.text = view.resources.getString(R.string.flight_range, transport.flightRange)
            }
            is Transport.WaterCraft->{
                view.let { Glide.with(it).load(transport.imageLink).into(transportDetailImageView) }
                transportDetailModelTextView.text = view.resources.getString(R.string.model, transport.model)
                flightRangeDetailTextView.text = view.resources?.getString(R.string.displacement, transport.displacement)
            }
            is Transport.RoadTransport->{
                view.let { Glide.with(it).load(transport.imageLink).into(transportDetailImageView) }
                transportDetailModelTextView.text = view.resources.getString(R.string.model, transport.model)
                flightRangeDetailTextView.text = view.resources.getString(R.string.fuelConsumption, transport.fuelConsumption)
            }
        }
    }


}