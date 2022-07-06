package com.example.viewmodelandnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.viewmodelandnavigation.R
import kotlinx.android.synthetic.main.fragment_button.*


class ButtonFragment : Fragment(R.layout.fragment_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linearTransportList.setOnClickListener {
    val action = ButtonFragmentDirections.actionButtonFragmentToListFragmentLayout(ListFragmentLayout.LINEAR_LAYOUT)
            findNavController().navigate(action)
        }
        gridTransportList.setOnClickListener {
           val action = ButtonFragmentDirections.actionButtonFragmentToListFragmentLayout(ListFragmentLayout.GRID_LAYOUT)
            findNavController().navigate(action)
        }
        staggeredTransportList.setOnClickListener {
            val action = ButtonFragmentDirections.actionButtonFragmentToListFragmentLayout(ListFragmentLayout.STAGGERED_LAYOUT)
            findNavController().navigate(action)
        }

    }

}