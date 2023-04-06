package com.jorbital.jorbichef

import com.apollographql.apollo3.ApolloClient
import com.jorbital.jorbichef.apollo.ApolloAPI
import com.jorbital.jorbichef.repository.TestRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::Platform)
    singleOf(::Greeting)

    single {
        ApolloClient.Builder()
            .serverUrl("http://192.168.50.19:8080/graphql")
            .build()
    }
    singleOf(::ApolloAPI)
    singleOf(::TestRepository)
}