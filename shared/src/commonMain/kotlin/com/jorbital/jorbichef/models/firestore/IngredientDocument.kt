package com.jorbital.jorbichef.models.firestore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IngredientDocument {
    @Serializable
    @SerialName("default")
    data class Default(
        val id: String,
        val name: String,
        val tagIds: List<String>,
        val resourceId: String
    ) : IngredientDocument()

    @Serializable
    @SerialName("custom")
    data class Custom(
        val id: String,
        val name: String,
        val tagIds: List<String>,
        val userId: String
    ) : IngredientDocument()
}