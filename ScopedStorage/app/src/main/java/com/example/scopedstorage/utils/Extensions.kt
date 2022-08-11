package com.example.contentprovider.utils

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean= false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}
fun haveQ(): Boolean{
    return Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q
}