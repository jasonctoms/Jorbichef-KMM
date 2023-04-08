package com.jorbital.jorbichef.api

import com.jorbital.jorbichef.models.firestore.IngredientDocument
import com.jorbital.jorbichef.models.firestore.TagDocument

// common tags people would want to add to their ingredients or recipes
val defaultTags = listOf(
    TagDocument.Default(id = "seasoning", name = "seasoning", resourceId = "tag_seasoning"),
    TagDocument.Default(id = "vegetable", name = "vegetable", resourceId = "tag_vegetable"),
    TagDocument.Default(id = "fruit", name = "fruit", resourceId = "tag_fruit"),
    TagDocument.Default(id = "meat", name = "meat", resourceId = "tag_meat"),
    TagDocument.Default(id = "nut", name = "nut", resourceId = "tag_nut"),
    TagDocument.Default(id = "bean", name = "bean", resourceId = "tag_bean"),
    TagDocument.Default(id = "grain", name = "grain", resourceId = "tag_grain"),
    TagDocument.Default(id = "protein", name = "protein", resourceId = "tag_protein"),
    TagDocument.Default(id = "dairy", name = "dairy", resourceId = "tag_dairy"),
    TagDocument.Default(id = "vegan", name = "vegan", resourceId = "tag_vegan"),
    TagDocument.Default(id = "vegetarian", name = "vegetarian", resourceId = "tag_vegetarian"),
    TagDocument.Default(id = "gluten-free", name = "gluten-free", resourceId = "tag_gluten_free"),
    TagDocument.Default(id = "pre-made", name = "pre-made", resourceId = "tag_pre_made"),
    TagDocument.Default(id = "spicy", name = "spicy", resourceId = "tag_spicy"),
    TagDocument.Default(id = "dessert", name = "dessert", resourceId = "tag_dessert"),
    TagDocument.Default(id = "breakfast", name = "breakfast", resourceId = "tag_breakfast"),
    TagDocument.Default(id = "lunch", name = "lunch", resourceId = "tag_lunch"),
    TagDocument.Default(id = "dinner", name = "dinner", resourceId = "tag_dinner"),
    TagDocument.Default(id = "snack", name = "snack", resourceId = "tag_snack"),
    TagDocument.Default(id = "side", name = "side", resourceId = "tag_side"),
    TagDocument.Default(id = "sauce", name = "sauce", resourceId = "tag_sauce"),
)

// common ingredients people would want to use in their recipes
val defaultIngredients = listOf(
    IngredientDocument.Default(
        id = "salt",
        name = "salt",
        tagIds = listOf("seasoning"),
        resourceId = "ingredient_salt",
    ),
    IngredientDocument.Default(
        id = "pepper",
        name = "pepper",
        tagIds = listOf("seasoning"),
        resourceId = "ingredient_pepper",
    ),
    IngredientDocument.Default(
        id = "onion",
        name = "onion",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_onion",
    ),
    IngredientDocument.Default(
        id = "garlic",
        name = "garlic",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_garlic",
    ),
    IngredientDocument.Default(
        id = "potato",
        name = "potato",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_potato",
    ),
    IngredientDocument.Default(
        id = "carrot",
        name = "carrot",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_carrot",
    ),
    IngredientDocument.Default(
        id = "celery",
        name = "celery",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_celery",
    ),
    IngredientDocument.Default(
        id = "broccoli",
        name = "broccoli",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_broccoli",
    ),
    IngredientDocument.Default(
        id = "cauliflower",
        name = "cauliflower",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_cauliflower",
    ),
    IngredientDocument.Default(
        id = "spinach",
        name = "spinach",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_spinach",
    ),
    IngredientDocument.Default(
        id = "tomato",
        name = "tomato",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_tomato",
    ),
    IngredientDocument.Default(
        id = "lettuce",
        name = "lettuce",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_lettuce",
    ),
    IngredientDocument.Default(
        id = "cucumber",
        name = "cucumber",
        tagIds = listOf("vegetable"),
        resourceId = "ingredient_cucumber",
    ),
    IngredientDocument.Default(
        id = "apple",
        name = "apple",
        tagIds = listOf("fruit"),
        resourceId = "ingredient_apple",
    ),
    IngredientDocument.Default(
        id = "banana",
        name = "banana",
        tagIds = listOf("fruit"),
        resourceId = "ingredient_banana",
    ),
    IngredientDocument.Default(
        id = "orange",
        name = "orange",
        tagIds = listOf("fruit"),
        resourceId = "ingredient_orange",
    ),
    IngredientDocument.Default(
        id = "tofu",
        name = "tofu",
        tagIds = listOf("protein", "vegan"),
        resourceId = "ingredient_tofu",
    ),
    IngredientDocument.Default(
        id = "chicken",
        name = "chicken",
        tagIds = listOf("meat", "protein"),
        resourceId = "ingredient_chicken",
    ),
    IngredientDocument.Default(
        id = "cashew",
        name = "cashew",
        tagIds = listOf("nut"),
        resourceId = "ingredient_cashew",
    ),
    IngredientDocument.Default(
        id = "almond",
        name = "almond",
        tagIds = listOf("nut"),
        resourceId = "ingredient_almond",
    ),
    IngredientDocument.Default(
        id = "peanut",
        name = "peanut",
        tagIds = listOf("nut"),
        resourceId = "ingredient_peanut",
    ),
    IngredientDocument.Default(
        id = "brown-rice",
        name = "brown rice",
        tagIds = listOf("grain"),
        resourceId = "ingredient_brown_rice",
    ),
    IngredientDocument.Default(
        id = "white-rice",
        name = "white rice",
        tagIds = listOf("grain"),
        resourceId = "ingredient_white_rice",
    ),
    IngredientDocument.Default(
        id = "quinoa",
        name = "quinoa",
        tagIds = listOf("grain"),
        resourceId = "ingredient_quinoa",
    )
)