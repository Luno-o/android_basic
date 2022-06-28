package com.example.lists1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState==null)
showButtonFragment()
    }
private fun showButtonFragment(){
    childFragmentManager.beginTransaction()
        .replace(R.id.mainFragmentContainer,ButtonFragment())
        .commit()
}

}