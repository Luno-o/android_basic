package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.network.Network
import kotlinx.android.synthetic.main.fragment_current_user.*


class CurrentUserFragment: Fragment(R.layout.fragment_current_user) {
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
userRepository.getUser({user->

    idTextView.text = user.id.toString()
    userNameTextView.text = user.username
    Glide.with(this)
        .load(user.avatar)
        .into(avatarImageView)
    Log.d("User", "dataset = done")
},{})
    }


}