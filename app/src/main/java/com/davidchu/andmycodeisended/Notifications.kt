package com.davidchu.andmycodeisended

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import java.time.LocalDateTime
import java.time.ZoneId

object Notifications {
    private const val channelId = "wutevs"
    enum class Trigger(@StringRes val notificationTitle: Int) {
        Code(R.string.alarm_title_code_trigger), Time(R.string.alarm_title_time_trigger)
    }

    fun setAlarm(context: Context, dateTime: LocalDateTime) {
        if (dateTime.isBefore(LocalDateTime.now()))
            return

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateTime.atZone(ZoneId.systemDefault()).toEpochSecond(), pendingIntent)
    }
    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.cancel(pendingIntent)
    }
    fun startFullScreenNotification(context: Context, trigger: Trigger) {
        val intent = Intent(context, FullScreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground_raw)
            .setContentTitle(context.getString(trigger.notificationTitle))
            .setContentText(context.getString(R.string.alarm_info))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Alarm", NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
        manager.notify(1, notificationBuilder.build())
    }
}