package com.example.logomaker.Activities

import androidx.multidex.MultiDexApplication
import com.example.logomaker.MySharePreferences.MySharePreference
import com.google.firebase.FirebaseApp

class AppClass : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appClass = this
        MySharePreference.getInstance(appClass)
        FirebaseApp.initializeApp(this)
    }

    companion object {
        private var appClass: AppClass? = null
        fun getintance(): AppClass? {
            return appClass
        }
    }
}