package com.jorbital.jorbichef.models

import kotlinx.datetime.LocalDate

data class Menu(
    val id: String,
    val date: LocalDate,
    val recipes: List<Recipe>,
)
