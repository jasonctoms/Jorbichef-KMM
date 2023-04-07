package com.jorbital.jorbichef.models

data class Recipe(
    val id: String,
    val userId: String,
    val name: String,
    val ingredients: List<RecipeIngredient>,
    val instructions: String,
    val url: String?,
    val imageUrl: String?,
    val tags: List<Tag>,
)

data class RecipeIngredient(
    val id: String,
    val amount: String,
    val ingredient: Ingredient
)