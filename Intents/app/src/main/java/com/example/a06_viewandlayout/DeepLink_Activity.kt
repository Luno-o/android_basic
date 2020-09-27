package com.example.a06_viewandlayout

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_deeplink.*

class DeepLink_Activity : AppCompatActivity(R.layout.activity_deeplink){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
handleIntentData()
    }
    fun handleIntentData(){
        intent.data?.path?.let{
            deeplinkText.text = it
        }
    }

}