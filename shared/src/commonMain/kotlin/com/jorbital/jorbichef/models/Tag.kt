package com.jorbital.jorbichef.models

data class Tag(
    val id: String,
    val name: String,
    val resourceId: String? = null,
    val userId: String? = null,
)
