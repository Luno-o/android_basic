package com.panko.viewpagerdialog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticleAdapter(private val screen: List<ArticleScreen>,fragment: Fragment):
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return screen.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen :ArticleScreen = screen[position]
        return ArticleFragment.newInstance(textRes = screen.article,drawRes = screen.draw)
    }

}