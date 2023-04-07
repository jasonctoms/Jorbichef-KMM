package com.jorbital.jorbichef.models

data class WeeklyMenu(
    val id: String,
    val userId: String,
    val monday: List<Recipe>,
    val tuesday: List<Recipe>,
    val wednesday: List<Recipe>,
    val thursday: List<Recipe>,
    val friday: List<Recipe>,
    val saturday: List<Recipe>,
    val sunday: List<Recipe>,
)
