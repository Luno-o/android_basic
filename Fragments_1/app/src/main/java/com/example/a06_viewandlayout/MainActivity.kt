package com.example.a06_viewandlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
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