package com.arya199.gemstone

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {
    val GEMSTONE_SHARED_PREFS = "Gemstone Shared Prefs"
    // Both are unix timestamp
    val LAST_LIVE_RATE_UPDATE = "live"
    val LAST_CURRENCY_LIST_UPDATE = "currency"

    fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context): SharedPreferences = context.getSharedPreferences(
        GEMSTONE_SHARED_PREFS, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.shouldLiveUpdate: () -> Boolean
        get() = {
            val timestamp = System.currentTimeMillis()
            if (lastLiveUpdate == 0L) true
            else {
                val difference = timestamp - lastLiveUpdate
                difference >= 30*60000
            }
        }
        set(value) = TODO()

    var SharedPreferences.lastLiveUpdate
        get() = getLong(LAST_LIVE_RATE_UPDATE, 0)
        set(value) {
            editMe {
                it.putLong(LAST_LIVE_RATE_UPDATE, value)
            }
        }

    var SharedPreferences.lastCurrencyListUpdate
        get() = getLong(LAST_CURRENCY_LIST_UPDATE, 0)
        set(value) {
            editMe {
                it.putLong(LAST_CURRENCY_LIST_UPDATE, value)
            }
        }
}