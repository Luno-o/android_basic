package com.panko.viewpagerdialog

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticleScreen (
    @StringRes val article: Int,@DrawableRes val draw: Int,val tag: ArticleTag)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            TODO("tag"))
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(article)
        parcel.writeInt(draw)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleScreen> {
        override fun createFromParcel(parcel: Parcel): ArticleScreen {
            return ArticleScreen(parcel)
        }

        override fun newArray(size: Int): Array<ArticleScreen?> {
            return arrayOfNulls(size)
        }
    }
}