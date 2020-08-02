package com.davidchu.andmycodeisended

import android.content.Context
import android.net.Uri
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Settings {
    enum class Keys {
        Prefs, Token, Vibrate, Music, Alarm, AlarmOn
    }
    fun getToken(context: Context): String? {
        return context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getString(Keys.Token.name, null)
    }
    fun setToken(context: Context, token: String) {
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putString(Keys.Token.name, token)
            .apply()
    }
    fun getVibrate(context: Context): Boolean {
        return context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getBoolean(Keys.Vibrate.name, false)
    }
    fun setVibrate(context: Context, vibrate: Boolean) {
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Keys.Vibrate.name, vibrate)
            .apply()
    }
    fun getMusic(context: Context): Uri? {
        val uriString = context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getString(Keys.Music.name, null)
        return if (uriString == null || uriString == "null") null else Uri.parse(uriString)
    }
    fun setMusic(context: Context, uri: Uri?) {
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putString(Keys.Music.name, uri.toString())
            .apply()
    }
    fun getAlarmDateTime(context: Context): LocalDateTime? {
        val epoch = context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getLong(Keys.Alarm.name, 0L)
        return if (epoch == 0L) null else Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
    fun setAlarmDateTime(context: Context, dateTime: LocalDateTime) {
        val epoch = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putLong(Keys.Alarm.name, epoch)
            .apply()
    }
    fun getAlarmOn(context: Context): Boolean {
        return context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getBoolean(Keys.AlarmOn.name, true)
    }
    fun setAlarmOn(context: Context, on: Boolean) {
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Keys.AlarmOn.name, on)
            .apply()
    }
}