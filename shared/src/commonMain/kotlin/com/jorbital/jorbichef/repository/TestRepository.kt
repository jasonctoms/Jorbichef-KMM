package com.jorbital.jorbichef.repository

import com.jorbital.jorbichef.models.Ingredient

class TestRepository() {
    suspend fun getIngredients() = emptyList<Ingredient>()
}