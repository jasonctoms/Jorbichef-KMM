package com.jorbital.jorbichef

import com.jorbital.jorbichef.api.FirestoreApi
import com.jorbital.jorbichef.auth.JorbichefAuth
import com.jorbital.jorbichef.repository.TestRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::Platform)
    singleOf(::Greeting)

    singleOf(::JorbichefAuth)
    singleOf(::FirestoreApi)
    singleOf(::TestRepository)
}