package com.panko.viewpagerdialog

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
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
        TabLayoutMediator(tabLayout,viewpager){
                tab, position ->
            tab.text =getString( R.string.articleHead_1)

        }.attach()


        viewpager.setPageTransformer(object : ViewPager2.PageTransformer{
            private val MIN_SCALE = 0.65f
            private val MIN_ALPHA = 0.3f
            override fun transformPage(page: View, position: Float) {
                if (position <-1){ // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0f);

                }
                else if (position <=1){ // [-1,1]

                    page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
                    page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
                    page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));

                }
                else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0f)
                }
            }

        })
    }



}