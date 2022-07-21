package com.skillbox.github.ui.repository_list

import android.util.Log
import com.skillbox.github.network.Network
import com.skillbox.github.network.RemoteRepository
import com.skillbox.github.network.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepoRepository {
   suspend fun getRepo():List<RemoteRepository>{
      return withContext(Dispatchers.Default){
          val response =  Network.gitHubApi.searchRepo()
          response
       }

    }

    suspend fun checkStarred(
        ownerName: String,
        repoName: String
    ): Boolean{
        return suspendCancellableCoroutine { continuation->
continuation.invokeOnCancellation {
    Network.gitHubApi.isStarred(ownerName,repoName).cancel()
}
        Network.gitHubApi.isStarred(ownerName,repoName).enqueue(
            object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.code()==204){
                        Log.d("Response"," Response code ${response.code()}")
                       continuation.resume(true)
                    }else continuation.resume(false)
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            }
        )
        }
    }

   suspend fun putStar(
        ownerName: String,
        repoName: String,
    ){
        suspendCancellableCoroutine{continuation->
            val call = Network.gitHubApi.putStar(ownerName,repoName)
            continuation.invokeOnCancellation {
                call.cancel()
            }
       call.enqueue(
            object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        continuation.resume(Unit)
                    }else if (response.code() == 304){
                            continuation.resumeWithException(error("Not add try again"))
                        }

                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            }
        )
        }
    }
   suspend fun unStar(
        ownerName: String,
        repoName: String,
   ){
       suspendCancellableCoroutine { continuation->
           val call = Network.gitHubApi.unStar(ownerName,repoName)
           continuation.invokeOnCancellation {
               call.cancel()
           }
        call.enqueue(
            object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        continuation.resume(Unit)
                    }else{
                        if (response.code() == 304){
                            continuation.resumeWithException(error("Not deleted try again"))
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            }
        )
       }
    }
}