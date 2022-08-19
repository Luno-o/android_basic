package com.example.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class BatteryBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive")
        context ?: return
        val isLow = intent?.action == Intent.ACTION_BATTERY_LOW
        if (isLow) {
            showLowBatteryNotification(context)
        } else hideBatteryNotification(context)
    }

    private fun showLowBatteryNotification(context: Context) {

        val notification = NotificationCompat.Builder(context, NotificationChannels.INFO_CHANNEL_ID)
            .setContentTitle("Battery is low")
            .setSmallIcon(R.drawable.ic_notifications)
            .build()
        NotificationManagerCompat.from(context)
            .notify(BATTERY_NOTIFICATION_ID, notification)
    }

    private fun hideBatteryNotification(context: Context) {
        NotificationManagerCompat.from(context)
            .cancel(BATTERY_NOTIFICATION_ID)
    }

    companion object {
        private const val BATTERY_NOTIFICATION_ID = 5432
    }
}