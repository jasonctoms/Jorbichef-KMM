package com.jorbital.jorbichef

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::Platform)
    singleOf(::Greeting)
}