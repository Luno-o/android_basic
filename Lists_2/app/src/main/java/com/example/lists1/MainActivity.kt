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
    private var idCounter:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permission = baseContext.checkSelfPermission(android.Manifest.permission.INTERNET)
        if (permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),REQUEST_CODE_INTERNET)
        }else  Log.d("internet", "Internet: $permission")

        if (savedInstanceState==null){
            showFragment()
            Log.d("mainactivity", "new fragment: $permission")
        }
    }
    private fun showFragment(){
supportFragmentManager.beginTransaction()
        .replace(R.id.container,MainFragment())
        .commit()
    }
    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments){
            if (fragment.isVisible){
                val childFragment = fragment.childFragmentManager
                if (childFragment.backStackEntryCount > 0){
                    childFragment.popBackStack()
                    Log.d("Main backstack", childFragment.backStackEntryCount.toString())
                }
                else
                    super.onBackPressed()
            }
        }

    }
    fun getId(): Long{
        return idCounter++
    }
}