package com.skillbox.github.ui.current_user

import android.util.Log
import com.skillbox.github.network.Network
import com.skillbox.github.network.RemoteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserRepository {
   suspend fun getUser(): RemoteUser{
       return Network.gitHubApi.searchUser()
    }
    suspend fun getUserFollows(): List<RemoteUser>{
        return Network.gitHubApi.userFollows()
    }
}