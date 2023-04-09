package com.jorbital.jorbichef.android

import android.app.Application
import com.jorbital.jorbichef.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class JorbichefApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@JorbichefApplication)
            androidLogger()
        }
    }
}