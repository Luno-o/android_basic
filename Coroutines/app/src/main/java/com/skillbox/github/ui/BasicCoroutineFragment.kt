package com.skillbox.github.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.skillbox.github.R
import kotlinx.coroutines.*
import java.math.BigInteger
import java.util.*
import kotlin.random.Random
import kotlin.random.asJavaRandom


class BasicCoroutineFragment : Fragment() {
private val fragmentScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {

        }
        viewLifecycleOwner.lifecycleScope.launch{

        }
asyncExample()
    }
private fun asyncExample(){
val fragmentIOScope = CoroutineScope(Dispatchers.IO)
    fragmentIOScope.launch {
        (0..100).map {
            async {
                calculateNumber()
            }
        }
            .map { it.await() }
    }


}
private suspend fun calculateNumber():BigInteger{
   return withContext(Dispatchers.Default){
    Log.d("BasicCoroutineFragment","calculate number = ${Thread.currentThread()}")
       BigInteger.probablePrime(2000, Random.asJavaRandom())
   }

}
    private fun changeThreadExample(){
val fragmentIOScope = CoroutineScope(Dispatchers.IO)
fragmentIOScope.launch {
    (0..200).forEach {
        Log.d("BasicCoroutineFragment","start from thread = ${Thread.currentThread()}")
        delay(100)
        Log.d("BasicCoroutineFragment","end from thread = ${Thread.currentThread()}")
    }
}

    }
    private fun calculateNumberExample(){
        fragmentScope.launch {
Log.d("BasicCoroutineFragment","launch inside from thread = ${Thread.currentThread()}")
            calculateNumber()
Log.d("BasicCoroutineFragment","launch inside from thread = ${Thread.currentThread()}")
        }

    }
}