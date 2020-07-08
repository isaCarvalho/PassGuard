package com.example.passguard.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import com.example.passguard.MainActivity
import com.example.passguard.R

class NotificationController private constructor(private val context: Context)
{
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder : Notification.Builder

    private val channelId = "com.example.passguard"
    private val description = "code notification"

    fun pushNotification()
    {
        codeGenerator()

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.GREEN
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(context, channelId)
        }
        else
            builder = Notification.Builder(context)

        builder.setContentTitle("Código de autenticação")
            .setContentText(code)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, builder.build())
    }

    companion object
    {
        private var notificationController: NotificationController? = null
        private lateinit var code : String

        private fun codeGenerator() {
            code = (100000..999999).random().toString()
        }

        fun getCode() = code

        fun getInstance(context: Context) : NotificationController
        {
            if (notificationController == null)
                notificationController = NotificationController(context)

            return notificationController!!
        }
    }
}