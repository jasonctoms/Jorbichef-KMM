package com.jorbital.jorbichef.models.firestore

import kotlinx.serialization.Serializable

/**
 * The Firestore document that contains a list of recipe IDs for each day of the week.
 */
@Serializable
data class WeeklyMenuDocument(
    val id: String = "menu",
    val userId: String,
    val monday: List<String>,
    val tuesday: List<String>,
    val wednesday: List<String>,
    val thursday: List<String>,
    val friday: List<String>,
    val saturday: List<String>,
    val sunday: List<String>,
)
