package com.example.backgroundwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class BatteryBroadcastReceiver : BroadcastReceiver() {
    private var isBatteryLow = false
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive")
        context ?: return
        val isLow = intent?.action == Intent.ACTION_BATTERY_LOW
        isBatteryLow = isLow
    }
    fun isBatteryLow():Boolean{
        return isBatteryLow
    }

}