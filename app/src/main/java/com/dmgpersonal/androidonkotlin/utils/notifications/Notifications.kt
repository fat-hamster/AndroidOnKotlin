package com.dmgpersonal.androidonkotlin.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dmgpersonal.androidonkotlin.MyApp
import com.dmgpersonal.androidonkotlin.utils.NOTIFICATION_HIGH_CHANNEL

val NOTIFICATION_ID = 101

fun pushNotification(title: String, body: String) {
    val notificationManager = MyApp.appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notification = NotificationCompat.Builder(MyApp.appContext, NOTIFICATION_HIGH_CHANNEL).apply {
        setContentTitle(title)
        setContentText(body)
        setSmallIcon(androidx.appcompat.R.drawable.abc_star_black_48dp)
        priority = NotificationCompat.PRIORITY_MAX
    }.build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.createNotificationChannel(NotificationChannel(
            NOTIFICATION_HIGH_CHANNEL,
            NOTIFICATION_HIGH_CHANNEL,
            NotificationManager.IMPORTANCE_HIGH
        ).also { it.description = "Канал для высокоприоритетных сообщений" })
    }

    notificationManager.notify(NOTIFICATION_ID, notification)
}