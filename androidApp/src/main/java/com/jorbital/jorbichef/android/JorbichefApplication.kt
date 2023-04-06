package com.jorbital.jorbichef.android

import android.app.Application
import com.jorbital.jorbichef.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class JorbichefApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@JorbichefApplication)
            androidLogger()
            modules(commonModule)
        }
    }
}