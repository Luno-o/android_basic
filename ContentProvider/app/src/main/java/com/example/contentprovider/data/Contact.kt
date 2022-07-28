package com.example.contentprovider.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val id: Long,
    val name: String,
    val phones: List<String>,
    val mails: List<String>
):Parcelable {
}