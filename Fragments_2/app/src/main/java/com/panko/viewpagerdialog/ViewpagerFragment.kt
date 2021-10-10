package com.panko.viewpagerdialog

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_viewpager.*

class ViewpagerFragment : Fragment(R.layout.fragment_viewpager){
    private val screens: List <ArticleScreen> = listOf(
        ArticleScreen(R.string.article_1,R.drawable.viewpager_cat_and_dog),
        ArticleScreen(R.string.article_1,R.drawable.viewpager_cat_and_dog),
        ArticleScreen(R.string.article_1,R.drawable.viewpager_cat_and_dog)
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArticleAdapter(screens,this)
        viewpager.adapter = adapter
dots_indicator.setViewPager2(viewpager)
    }


}