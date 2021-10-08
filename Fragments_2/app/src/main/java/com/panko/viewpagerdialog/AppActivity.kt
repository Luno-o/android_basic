package com.panko.viewpagerdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_app.*

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
if (savedInstanceState==null)showFragment()
    }
    private fun showFragment(){
supportFragmentManager.beginTransaction()
    .replace(R.id.container,ViewpagerFragment())
    .commit()
    }
}
