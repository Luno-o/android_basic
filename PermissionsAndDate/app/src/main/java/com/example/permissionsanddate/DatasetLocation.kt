package com.example.permissionsanddate

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant
@Parcelize
data class DatasetLocation(val id: Int, var timeStamp:Instant, val location: Location, val imageLink:String)
    :Parcelable
