package com.skillbox.github.ui.current_user

import android.util.Log
import com.skillbox.github.network.Network
import com.skillbox.github.network.RemoteUser
import com.skillbox.github.network.ServerItemsWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserRepository {
    fun getUser(onComplete: (RemoteUser)->Unit,
    onError: (Throwable)->Unit){
        Network.gitHubApi.searchUser().enqueue(
            object : Callback<RemoteUser> {
                override fun onResponse(
                    call: Call<RemoteUser>,
                    response: Response<RemoteUser>
                ) {
                        Log.d("onComplete", "response = ${response.body()}")
                    if (response.isSuccessful){
                        response.body()?.let { onComplete(it) }
                    }else{
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}