package com.jorbital.jorbichef.models.firestore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class TagDocument {
    @Serializable
    @SerialName("default")
    data class Default(val id: String, val name: String, val resourceId: String) : TagDocument()

    @Serializable
    @SerialName("custom")
    data class Custom(val id: String, val name: String, val userId: String) : TagDocument()
}
