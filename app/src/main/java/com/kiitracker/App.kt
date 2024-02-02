package com.kiitracker

import android.app.Application
import com.kiitracker.data.local.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Prefs.init(this)
    }
}