package com.example.a02_toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Coltoolbar.title = resources.getString(R.string.ChJPriloje)
initToolbar()
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { (toast("Navigation clicked")) }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_1 -> {
                    toast("Action 1 clicked")
                    true
                }
                R.id.action_2 -> {
                    toast("Action 2 clicked")
                    true
                }
                R.id.action_3 -> {
                    toast("Action 3 clicked")
                    true
                }
                else -> false
            }
        }
        val searchItem = toolbar.menu.findItem(R.id.search_action)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                toast("Search expanded")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                toast("Search collapsed")
                return true
            }

        })

    }
}

