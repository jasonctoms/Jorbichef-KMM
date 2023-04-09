package com.jorbital.jorbichef.di

import com.jorbital.jorbichef.Database
import com.jorbital.jorbichef.Greeting
import com.jorbital.jorbichef.Platform
import com.jorbital.jorbichef.api.FirestoreApi
import com.jorbital.jorbichef.auth.JorbichefAuth
import com.jorbital.jorbichef.database.DriverFactory
import com.jorbital.jorbichef.repository.TestRepository
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) =
    startKoin {
        appDeclaration()
        modules(
            platformModule(),
            commonModule,
        )
    }

val commonModule = module {
    singleOf(::Platform)
    singleOf(::Greeting)

    singleOf(::JorbichefAuth)
    singleOf(::FirestoreApi)
    singleOf(::TestRepository)

    single {
        val driver = get<DriverFactory>().createDriver()
        Database(driver)
    }
}


// iOS requires empty constructor
fun initKoin() = initKoin {}