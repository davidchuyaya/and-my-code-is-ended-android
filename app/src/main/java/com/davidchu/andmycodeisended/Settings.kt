package com.davidchu.andmycodeisended

import android.content.Context

object Settings {
    enum class Keys {
        Prefs, Token
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
}