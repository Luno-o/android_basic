package com.panko.viewpagerdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.fragment_viewpager.*
import java.util.ArrayList

class AppActivity : AppCompatActivity() , FilterClickListener{
    private var checkedTags: BooleanArray = BooleanArray(ArticleTag.values().size)
    private val screens: List<ArticleScreen> = mutableListOf(
            ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog,ArticleTag.NEWS),
            ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog, ArticleTag.POLITIC),
            ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog,ArticleTag.TECH)
    )
    private var filteredScreens = ArrayList<ArticleScreen>()
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

if (savedInstanceState==null)
    checkedTags.all { true }
    showFragment()
    }

    private fun showFragment(){
        if (filteredScreens.isEmpty()) filteredScreens.addAll(screens)
supportFragmentManager.beginTransaction()
    .replace(R.id.container,ViewpagerFragment.newInstance(filteredScreens))
    .commit()
    }

    override fun onSelectedItems(checkedBoxes: BooleanArray) {
        checkedTags = checkedBoxes
        val filteredListOfTags = ArticleTag.values().filter {
            checkedTags[it.ordinal]
        }
        filteredScreens = ArrayList(screens.filter {
            var hasTag = false
            for (tag in filteredListOfTags)
            {
                if(it.tag == tag) hasTag = true

            }

            hasTag
        })
        Log.d("filtered screens",filteredScreens.size.toString())
showFragment()
    }
    fun getBooleanArray():BooleanArray{
        return checkedTags
    }
}
