package com.davidchu.andmycodeisended

import android.content.Context
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Settings {
    enum class Keys {
        Prefs, Token, Alarm, AlarmOn
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
    private fun getAlarmEpoch(context: Context): Long? {
        val epoch = context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getLong(Keys.Alarm.name, 0L)
        return if (epoch != 0L) epoch else null
    }
    fun getAlarmDateTime(context: Context): LocalDateTime? {
        val epoch = getAlarmEpoch(context)
        return if (epoch == null) null else Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
    fun setAlarmEpoch(context: Context, epoch: Long) {
        context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .edit()
            .putLong(Keys.Alarm.name, epoch)
            .apply()
    }
    fun setAlarmDateTime(context: Context, dateTime: LocalDateTime) {
        val epoch = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
        setAlarmEpoch(context, epoch)
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