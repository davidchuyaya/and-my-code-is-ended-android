package com.davidchu.andmycodeisended

import android.content.Context
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
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
    fun getAlarmEpoch(context: Context): Long? {
        val epoch = context.getSharedPreferences(Keys.Prefs.name, Context.MODE_PRIVATE)
            .getLong(Keys.Alarm.name, -1)
        return if (epoch != -1L) epoch else null
    }
    fun getAlarmTime(context: Context): LocalTime? {
        val epoch = getAlarmEpoch(context)
        return if (epoch == null) null else Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault()).toLocalTime()
    }
    fun getAlarmDate(context: Context): LocalDate? {
        val epoch = getAlarmEpoch(context)
        return if (epoch == null) null else Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault()).toLocalDate()
    }
    fun setAlarmEpoch(context: Context, epoch: Long) {
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