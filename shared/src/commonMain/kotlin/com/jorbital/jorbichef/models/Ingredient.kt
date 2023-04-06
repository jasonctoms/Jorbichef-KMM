package com.jorbital.jorbichef.models

enum class IngredientType {
    HERB,
    SPICE,
    PROTEIN,
    VEGETABLE,
    FRUIT,
    CARB,
    BASIC,
    OTHER
}

data class Ingredient(var id: String, var name: String, var type: IngredientType)