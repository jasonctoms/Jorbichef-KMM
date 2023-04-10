package com.jorbital.jorbichef.models.firestore

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDocument(
    val id: String,
    val name: String,
    val userId: String,
    val instructions: String?,
    val url: String?,
    val imageUrl: String?,
    val tagIds: List<String>,
)

@Serializable
data class RecipeIngredientDocument(
    val id: String,
    val amount: String,
)