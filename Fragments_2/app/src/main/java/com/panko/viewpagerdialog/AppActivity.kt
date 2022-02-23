package com.panko.viewpagerdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.fragment_viewpager.*
import java.util.ArrayList

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

if (savedInstanceState==null)
    showFragment()
    }

    private fun showFragment(){
supportFragmentManager.beginTransaction()
    .replace(R.id.container,ViewpagerFragment())
    .commit()
    }

}
