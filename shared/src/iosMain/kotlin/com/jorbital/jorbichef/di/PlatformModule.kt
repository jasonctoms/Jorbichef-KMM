package com.jorbital.jorbichef.di

import com.jorbital.jorbichef.database.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory() }
}