package com.example.backgroundwork

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import timber.log.Timber

class DownloadService : Service() {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    override fun onBind(p0: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate from ${Thread.currentThread().name}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra(FirstFragment.KEY_URL)
        Timber.d("onStartCommand from ${Thread.currentThread().name}")
        if (url != null) {
            downloadFile(url)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadFile(url: String) {
        startForeground(NOTIFICATION_ID,createNotification(0,0))
        coroutineScope.launch {
            Timber.d("download started")
            DownloadState.changeDownloadState(true)
            val maxProgress = 5
            (0 until maxProgress).forEach {
                Timber.d("Download progress = ${it + 1}/$maxProgress")
                val updateNotification = createNotification(it+1,maxProgress)
                NotificationManagerCompat.from(this@DownloadService)
                    .notify(NOTIFICATION_ID,updateNotification)
                delay(1000)
            }
            Timber.d("Download complete")
            DownloadState.changeDownloadState(false)
            stopForeground(true)
            stopSelf()
        }
    }
private fun createNotification(progress: Int,maxProgress: Int): Notification{
return Notification.Builder(this,NotificationChannels.INFO_CHANNEL_ID)
    .setContentTitle("Download progress")
    .setSmallIcon(android.R.drawable.stat_sys_download)
    .setProgress(maxProgress,progress,false)
    .setOnlyAlertOnce(true)
    .build()
}
//    private fun showSynchronizingNotification(context: Context) {
//
//        val notificationBuilder =
//            NotificationCompat.Builder(context, NotificationChannels.INFO_CHANNEL_ID)
//                .setContentTitle("Synchronizing")
//                .setContentText("Synchronize in progress")
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setSmallIcon(R.drawable.ic_notifications)
//
//        val maxProgress = 10
//        lifecycleScope.launch {
//            (0 until maxProgress).forEach { progress ->
//                val notification = notificationBuilder
//                    .setProgress(maxProgress, progress, false)
//                    .build()
//                NotificationManagerCompat.from(requireContext())
//                    .notify(PROGRESS_NOTIFICATION_ID, notification)
//                delay(500)
//            }
//            val finalNotification = notificationBuilder
//                .setContentText("Synchronizing is completed")
//                .setProgress(0, 0, false)
//                .build()
//            NotificationManagerCompat.from(requireContext())
//                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
//            delay(1000)
//            NotificationManagerCompat.from(requireContext())
//                .cancel(PROGRESS_NOTIFICATION_ID)
//            withContext(Dispatchers.Main) {
//                binding.synchronizeButton.isEnabled = true
//            }
//        }
//    }
        override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        coroutineScope.cancel()
    }
companion object{
    private const val NOTIFICATION_ID = 4323
}

}