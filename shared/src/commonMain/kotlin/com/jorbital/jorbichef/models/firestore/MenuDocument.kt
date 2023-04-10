package com.jorbital.jorbichef.models.firestore

import kotlinx.serialization.Serializable

@Serializable
data class MenuDocument(
    val id: String,
    val userId: String,
    val date: String,
    val recipeId: String,
)
