package com.delicia.app.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "DeliciaAppPrefs"
    private const val IS_LOGGED_IN = "isLoggedIn"
    private const val USER_EMAIL = "userEmail"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginState(context: Context, isLoggedIn: Boolean, email: String? = null) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(IS_LOGGED_IN, false)
    }

    fun getUserEmail(context: Context): String? {
        return getPreferences(context).getString(USER_EMAIL, null)
    }

    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear() // Borra todos los datos de SharedPreferences
        editor.apply()
    }
    fun logout(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

}