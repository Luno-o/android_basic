package com.skillbox.github.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true) @Parcelize
data class Owner(
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
): Parcelable