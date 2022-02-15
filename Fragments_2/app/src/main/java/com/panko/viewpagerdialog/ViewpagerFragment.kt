package com.panko.viewpagerdialog

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

class ViewpagerFragment : Fragment(R.layout.fragment_viewpager) {
    private val screens: List<ArticleScreen> = listOf(
        ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog),
        ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog),
        ArticleScreen(R.string.article_1, R.drawable.viewpager_cat_and_dog)
    )
    private val articleTags = arrayOf("NEWS",
    "POLITIC",
    "TECH")
    private val checkedTags = BooleanArray(
        articleTags.size)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArticleAdapter(screens, this)

        viewpager.adapter = adapter
        dots_indicator.setViewPager2(viewpager)
        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            tab.text = getString(R.string.articleHead_1)
        }.attach()
      for(i in checkedTags.indices){
          checkedTags[i] = true
      }
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
            when (it.itemId)
            {
                R.id.filterMenuButton->{
                AlertDialog.Builder(activity)
                    .setTitle("Выберете категории")
                    .setMultiChoiceItems(articleTags,checkedTags){_,which: Int,isChecked:Boolean->
                        checkedTags[which]=isChecked
                    }
                    .create()
                    .show()
                    print(checkedTags.toString())
                    true}
                else -> false
            }
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

                            page.scaleX = max(MIN_SCALE, 1 - abs(position));
                            page.scaleY = max(MIN_SCALE, 1 - abs(position));
                            page.alpha = max(MIN_ALPHA, 1 - abs(position));

                        }
                        else -> { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            page.alpha = 0f
                        }
                    }
                }

            })
        }


    }