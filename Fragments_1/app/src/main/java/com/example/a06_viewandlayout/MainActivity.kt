package com.example.a06_viewandlayout

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabletSize = resources.getBoolean(R.bool.isTablet)


        if (savedInstanceState==null) showLoginFragment()

    }

    private fun showLoginFragment(){

            supportFragmentManager.beginTransaction()
                .add(R.id.container,LoginFragment())
                .commit()              //ассинхронное добавление


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
}