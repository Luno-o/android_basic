package com.example.lists1

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_INTERNET = 264
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permission = baseContext.checkSelfPermission(android.Manifest.permission.INTERNET)
        if (permission == PackageManager.PERMISSION_DENIED){
            Log.d("internet", "Internet:  no $permission")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),REQUEST_CODE_INTERNET)
        }else  Log.d("internet", "Internet: $permission")

        if (savedInstanceState==null){
            showFragment()
        }
    }
    private fun showFragment(){
supportFragmentManager.beginTransaction()
        .replace(R.id.container,ListFragment())
        .commit()
    }

}