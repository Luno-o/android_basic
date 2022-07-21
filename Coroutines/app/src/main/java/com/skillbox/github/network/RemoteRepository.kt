package com.skillbox.github.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true) @Parcelize
data class RemoteRepository(
    val id: String,
    val name: String,
    val owner : Owner
):Parcelable