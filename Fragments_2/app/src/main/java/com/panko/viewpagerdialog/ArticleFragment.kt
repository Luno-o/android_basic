package com.panko.viewpagerdialog

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_viewpager.*


class ArticleFragment : Fragment(R.layout.fragment_article) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.setImageResource(requireArguments().getInt(KEY_DRAW))
        textView.setText(requireArguments().getInt(KEY_TEXT))

    }

    companion object {
        private val tags:List<ArticleTag> = listOf()
        private const val KEY_TEXT = "resource text"
        private const val KEY_DRAW = "resource draw"
        fun newInstance(@StringRes textRes: Int, @DrawableRes drawRes: Int): ArticleFragment {
            return ArticleFragment().withArguments {
                putInt(KEY_TEXT, textRes)
                putInt(KEY_DRAW, drawRes)
            }
        }
    }
}