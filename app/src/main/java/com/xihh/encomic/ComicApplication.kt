package com.xihh.encomic

import android.app.Application

class ComicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}

lateinit var appContext: Application
