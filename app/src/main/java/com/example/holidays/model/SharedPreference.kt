package com.example.holidays.model

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class PreferencesUtils {

    companion object {

        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"
        const val GOOGLE_EMAIL: String = "GOOGLE_EMAIL"
        const val GOOGLE_ID: String = "GOOGLE_ID"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.googleEmail: String?
    get() = getString(PreferencesUtils.GOOGLE_EMAIL, "")
    set(value) = editMe { it.putString(PreferencesUtils.GOOGLE_EMAIL, value) }

var SharedPreferences.googleId: String?
    get() = getString(PreferencesUtils.GOOGLE_ID, "")
    set(value) = editMe { it.putString(PreferencesUtils.GOOGLE_ID, value) }
