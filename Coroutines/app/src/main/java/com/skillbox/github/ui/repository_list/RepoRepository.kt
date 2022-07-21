package com.skillbox.github.ui.repository_list

import android.util.Log
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
    fun checkStarred(
        ownerName: String,
        repoName: String,
        onComplete: (Boolean)->Unit,
        onError: (Throwable)->Unit
    ){
        Network.gitHubApi.isStarred(ownerName,repoName).enqueue(
            object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.code()==204){
                        Log.d("Response"," Response code ${response.code()}")
                        onComplete(true)
                    }else onComplete(false)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun putStar(
        ownerName: String,
        repoName: String,
        onComplete: (Boolean)->Unit,
        onError: (Throwable)->Unit
    ){
        Network.gitHubApi.putStar(ownerName,repoName).enqueue(
            object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        onComplete(true)
                    }else{
                        if (response.code() == 304){
                            onComplete(false)
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
    fun unStar(
        ownerName: String,
        repoName: String,
        onComplete: (Boolean)->Unit,
        onError: (Throwable)->Unit
    ){
        Network.gitHubApi.unStar(ownerName,repoName).enqueue(
            object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        onComplete(true)
                    }else{
                        if (response.code() == 304){
                            onComplete(false)
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}