package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify
import kotlin.random.Random

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        when (message.data["type"]) {
            MessageType.CHAT_MESSAGE.name -> {
                val userId = message.data["userId"]?.toLongOrNull()
                val userName = message.data["user_name"]
                val messageText = message.data["text"]
                if (userId != null && userName != null && messageText != null) {
                    showMessageNotification(userId, userName, messageText)
                }

            }
            MessageType.STOCKS.name -> {
                val title = message.data["title"]
                val description = message.data["description"]
                val imageUrl = message.data["imageUrl"]
                if (title != null && description != null) {
                    showStockNotification(title, description, imageUrl)
                }
            }
            else -> {}
        }
    }

    private fun showStockNotification(title: String, description: String, imageUrl: String?) {
        val largeIcon = imageUrl?.let {
            loadWithGlide(it)
        }
        val groupId = "group id Stock"
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)
        val notificationBuilder =
            NotificationCompat.Builder(this, NotificationChannels.INFO_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_notifications)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setGroup(groupId)

        if (largeIcon != null) {
            notificationBuilder.setLargeIcon(largeIcon)
        }
        val notification = notificationBuilder.build()

        val summaryNotification =  NotificationCompat.Builder(this,NotificationChannels.INFO_CHANNEL_ID)
            .setContentTitle("Summary")
            .setContentText("$description from time ${System.currentTimeMillis()}")
            .setSmallIcon(R.drawable.ic_message)
            .setGroup(groupId)
            .setGroupSummary(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .build()
        NotificationManagerCompat.from(this)
            .notify(543,summaryNotification)
        NotificationManagerCompat.from(this).notify(getNotificationId(), notification)
    }

    private fun getNotificationId(): Int {
        return Random.nextInt()
    }

    private fun loadWithGlide(url: String): Bitmap? {
        return Glide.with(this).asBitmap().load(url).submit().get()
    }

    private fun showMessageNotification(userId: Long, userName: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        // intent.putExtra()
        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.bird1)
        val notification = NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("You have a new message from $userName")
            .setContentText("$message from time ${System.currentTimeMillis()}")
            .setSmallIcon(R.drawable.ic_message)
            .setLargeIcon(largeIcon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .build()
        NotificationManagerCompat.from(this)
            .notify(userId.toInt(), notification)
    }
}