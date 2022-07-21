package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.network.Network
import kotlinx.android.synthetic.main.fragment_current_user.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CurrentUserFragment: Fragment(R.layout.fragment_current_user) {
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
        val deferredResultUser = lifecycleScope.async {
userRepository.getUser()
        }
           val deferredResult = lifecycleScope.async {
userRepository.getUserFollows()
        }
            val user = deferredResultUser.await()
            val followings = deferredResult.await()
            val bufferedString = StringBuffer()
            for (remoteUser in followings){
                bufferedString.append(remoteUser.username).append("\n")
            }
    idTextView.text = user.id.toString()
    userNameTextView.text = user.username
    Glide.with(view!!)
        .load(user.avatar)
        .into(avatarImageView)
followingTextView.text = bufferedString.toString()
        }
    }


}