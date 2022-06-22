package com.panko.viewpagerdialog

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleScreen (
    @StringRes val article: Int,@DrawableRes val draw: Int,val tag: ArticleTag)
    : Parcelable