package com.delicia.app.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "DeliciaAppPrefs"
    private const val IS_LOGGED_IN = "isLoggedIn"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginState(context: Context, isLoggedIn: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(IS_LOGGED_IN, false)
    }
}