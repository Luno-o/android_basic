package com.example.a06_viewandlayout

import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment: Fragment(R.layout.fragment_deatail){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameFriend = requireView().findViewById<TextView>(R.id.bestFriendName)
        nameFriend.text = requireArguments().getString(KEY_TEXT)
    }
    companion object {

        private const val KEY_TEXT = "key_text_detail"

        fun newInstance(text: String): DetailFragment {
            return DetailFragment().withArguments { putString(KEY_TEXT, text) }
        }
    }
}