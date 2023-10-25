package uz.ilhomjon.backgroundlocationaniqlash.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_NO_CREATE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.MutableLiveData
import uz.ilhomjon.backgroundlocationaniqlash.MainActivity
import uz.ilhomjon.backgroundlocationaniqlash.R

object MyData {


    fun createNotification(context: Context, title: String, contentText: String): Notification {
        val channelId = "my_channel_id" // Unikal kanal identifikatori
        createNotificationChannel(context, channelId, "My Channel")

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, notificationIntent, FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, notificationIntent, FLAG_NO_CREATE)
        }
        return Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background) // Xabarnomaning ikonasi
            .setContentTitle(title) // Xabarnomaning sarlavhasi
            .setContentText(contentText) // Xabarnomaning matni
            .setContentIntent(pendingIntent)
            .build()
    }

    fun createNotificationChannel(context: Context, channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}