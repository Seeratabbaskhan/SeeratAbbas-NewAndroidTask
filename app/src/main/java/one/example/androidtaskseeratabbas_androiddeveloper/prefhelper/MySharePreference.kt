package com.example.logomaker.MySharePreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.logomaker.Activities.AppClass

class MySharePreference private constructor(context: Context?) {
    var isEnglish: Boolean
        get() = pref!!.getBoolean("isEnglish", false)
        set(isEnglish) {
            pref!!.edit().putBoolean("isEnglish", isEnglish).apply()
        }

    companion object {
        private var instance: MySharePreference? = null
        private var pref: SharedPreferences? = null
        fun getInstance(context: Context?): MySharePreference? {
            if (instance == null || pref == null) {
                instance = MySharePreference(context)
            }
            return instance
        }
    }

    init {
        if (context != null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            pref = AppClass.getintance()?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        }
    }
}