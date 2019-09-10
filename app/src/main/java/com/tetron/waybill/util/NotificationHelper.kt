package com.tetron.waybill.util


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.tetron.waybill.R
import com.tetron.waybill.view.currentwaybill.CurrentWaybillActivity


class NotificationHelper {
    companion object {
        lateinit var ADMIN_CHANNEL_ID: String
        lateinit var notificationManager: NotificationManager

        fun makeStatusNotification(title: String?, body: String, context: Context): Notification {
            val notificationBuilder: NotificationCompat.Builder
            ADMIN_CHANNEL_ID = "${context.packageName}-${context.getString(R.string.app_name)}"
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannels(context)
            }

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder = NotificationCompat.Builder(context, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)


            val resultIntent = Intent(context, CurrentWaybillActivity::class.java)
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            notificationBuilder.apply {
                setContentIntent(resultPendingIntent)
            }


            return notificationBuilder.build()
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun setupNotificationChannels(context: Context) {
            val appName = context.getString(R.string.app_name)
            val adminChannelName = "$appName Channel"
            val adminChannelDescription = "Push message channel for $appName"

            val adminChannel: NotificationChannel
            adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
            adminChannel.description = adminChannelDescription
            adminChannel.enableLights(true)
            adminChannel.lightColor = Color.RED
            adminChannel.enableVibration(true)
            notificationManager.createNotificationChannel(adminChannel)
        }


    }
}