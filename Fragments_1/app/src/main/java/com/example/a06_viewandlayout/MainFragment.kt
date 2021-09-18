package com.example.a06_viewandlayout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainFragment : Fragment(R.layout.fragment_main), ItemSelectListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabletSize = resources.getBoolean(R.bool.isTablet)

     if(!tabletSize){

         if (savedInstanceState==null) showListFragment()
     }

    }
private fun showListFragment(){
    childFragmentManager.beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        .replace(R.id.mainFragmentContainer, ListFragment())
        .commit()
}
    override fun onItemSelected(item: String) {
        childFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.mainFragmentContainer, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

}
