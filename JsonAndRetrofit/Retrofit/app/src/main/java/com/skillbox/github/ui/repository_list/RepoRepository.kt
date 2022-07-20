package com.skillbox.github.ui.repository_list

import com.skillbox.github.network.Network
import com.skillbox.github.network.RemoteRepository
import com.skillbox.github.network.RemoteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class RepoRepository {
    fun getRepo(onComplete: (List<RemoteRepository>)->Unit,
                onError: (Throwable)->Unit){
        Network.gitHubApi.searchRepo().enqueue(
            object : Callback<List<RemoteRepository>>{
                override fun onResponse(
                    call: Call<List<RemoteRepository>>,
                    response: Response<List<RemoteRepository>>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            onComplete(it) }
                    }else{
onError(RuntimeException("Incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<List<RemoteRepository>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}