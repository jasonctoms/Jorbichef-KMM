package com.jorbital.jorbichef.repository

import com.jorbital.jorbichef.apollo.ApolloAPI
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.type.IngredientType

class TestRepository(private val apolloAPI: ApolloAPI) {
    suspend fun getIngredients() = apolloAPI.getIngredients()
        //.map { Ingredient(it.id, it.name) }
}