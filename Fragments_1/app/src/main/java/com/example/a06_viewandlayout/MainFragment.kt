package com.example.a06_viewandlayout

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class MainFragment : Fragment(R.layout.fragment_main), ItemSelectListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, ListFragment())
            .addToBackStack("list of friends")
            .commit()
    }

    override fun onItemSelected(item: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, DetailFragment.newInstance(item))
            .addToBackStack(item)
            .commit()
    }
}