package com.jorbital.jorbichef.apollo

import com.apollographql.apollo3.ApolloClient
import com.jorbital.jorbichef.FetchIngredientsQuery

class ApolloAPI(private val client: ApolloClient) {
    suspend fun getIngredients(): List<FetchIngredientsQuery.Ingredient> {
        val response = client.query(FetchIngredientsQuery()).execute()
        return response.data?.ingredients ?: emptyList()
    }
}