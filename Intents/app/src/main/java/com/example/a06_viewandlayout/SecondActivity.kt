package com.example.a06_viewandlayout

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.invoke
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val myActionCall = prepareCall(ActivityResultContracts.Dial()) {
        it ?: toast("Call was cancelled")
        toast("Call was finished")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        callButton.setOnClickListener {

            val number: String = editPhoneNumber.text.toString()
            dialPhoneNumber(number)
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        myActionCall(phoneNumber)
    }


    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
