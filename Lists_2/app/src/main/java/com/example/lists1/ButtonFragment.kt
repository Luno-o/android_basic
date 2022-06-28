package com.example.lists1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_button.*


class ButtonFragment : Fragment(R.layout.fragment_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linearTransportList.setOnClickListener {
    showLinearList()
        }
        gridTransportList.setOnClickListener {
            showGridList()
        }
        staggeredTransportList.setOnClickListener {
            showStaggeredList()
        }

    }
private fun showLinearList(){
    parentFragmentManager.beginTransaction()
        .replace(R.id.mainFragmentContainer,ListFragmentLinearLayout())
        .addToBackStack(null)
        .commit()
}
    private fun showGridList(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer,ListFragmentGridLayout())
            .addToBackStack(null)
            .commit()
    }
    private fun showStaggeredList(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer,ListFragmentStaggeredLayout())
            .addToBackStack(null)
            .commit()
    }

}