package com.panko.viewpagerdialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

class ViewpagerFragment : Fragment(R.layout.fragment_viewpager) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screens = requireArguments().getParcelableArrayList<ArticleScreen>(KEY_SCREEN_ARRAY)!!.toList()
        val adapter = ArticleAdapter(screens, this)
        viewpager.adapter = adapter
        dots_indicator.setViewPager2(viewpager)
        TabLayoutMediator(tabLayout, viewpager) { tab, _ ->
            tab.text = getString(R.string.articleHead_1)
        }.attach()

        buttonBadge.setOnClickListener {
           val randomIndex = Random.nextInt(0,tabLayout.tabCount)
            if (tabLayout.selectedTabPosition!=randomIndex){
            tabLayout.getTabAt(randomIndex)?.orCreateBadge?.apply {

                    if (number!=0){
                        number++
                    }else{
                        number = 1
                    }
                    badgeGravity = BadgeDrawable.TOP_END}
                }


        }
        toolbar.setOnMenuItemClickListener {

                    FilterDialogFragment().show(childFragmentManager,"filterTagDialog")
            true

        }

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
            }
        })
            viewpager.setPageTransformer(object : ViewPager2.PageTransformer {
                private val MIN_SCALE = 0.65f
                private val MIN_ALPHA = 0.3f
                override fun transformPage(page: View, position: Float) {
                    when {
                        position < -1 -> { // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            page.alpha = 0f

                        }
                        position <= 1 -> { // [-1,1]

                            page.scaleX = max(MIN_SCALE, 1 - abs(position))
                            page.scaleY = max(MIN_SCALE, 1 - abs(position))
                            page.alpha = max(MIN_ALPHA, 1 - abs(position))

                        }
                        else -> { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            page.alpha = 0f
                        }
                    }
                }

            })
        }

    companion object{
        private const val KEY_SCREEN_ARRAY = "Key_screen_array"
        fun newInstance(screens: ArrayList<ArticleScreen>):ViewpagerFragment{
            return ViewpagerFragment().withArguments { putParcelableArrayList(KEY_SCREEN_ARRAY, screens) }
        }
    }




}

