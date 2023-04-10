package com.jorbital.jorbichef.database

import com.jorbital.jorbichef.SelectAllMenus
import com.jorbital.jorbichef.SelectAllRecipes
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.models.Recipe
import com.jorbital.jorbichef.models.RecipeIngredient
import com.jorbital.jorbichef.models.Tag

data class RecipeParts(
    val id: String,
    val name: String,
    val instructions: String?,
    val url: String?,
    val imageUrl: String?,
    val ingredientId: String?,
    val ingredientName: String?,
    val ingredientResourceId: String?,
    val amount: String?,
    val ingredientTagId: String?,
    val ingredientTagName: String?,
    val ingredientTagResourceId: String?,
    val recipeTagId: String?,
    val recipeTagName: String?,
    val recipeTagResourceId: String?,
)
fun SelectAllRecipes.toRecipeParts() = RecipeParts(
    id = id,
    name = name,
    instructions = instructions,
    url = url,
    imageUrl = imageUrl,
    ingredientId = ingredientId,
    ingredientName = ingredientName,
    ingredientResourceId = ingredientResourceId,
    amount = amount,
    ingredientTagId = ingredientTagId,
    ingredientTagName = ingredientTagName,
    ingredientTagResourceId = ingredientTagResourceId,
    recipeTagId = recipeTagId,
    recipeTagName = recipeTagName,
    recipeTagResourceId = recipeTagResourceId,
)

fun SelectAllMenus.toRecipeParts() = RecipeParts(
    id = recipeId!!,
    name = name!!,
    instructions = instructions,
    url = url,
    imageUrl = imageUrl,
    ingredientId = ingredientId,
    ingredientName = ingredientName,
    ingredientResourceId = ingredientResourceId,
    amount = amount,
    ingredientTagId = ingredientTagId,
    ingredientTagName = ingredientTagName,
    ingredientTagResourceId = ingredientTagResourceId,
    recipeTagId = recipeTagId,
    recipeTagName = recipeTagName,
    recipeTagResourceId = recipeTagResourceId,
)

fun List<RecipeParts>.toRecipe() : Recipe {
    val recipeProperties = this.first()
    return Recipe(
        id = recipeProperties.id,
        name = recipeProperties.name,
        ingredients = this.groupBy { it.ingredientId }
            .map { (ingredientId, ingredientGroup) ->
                val ingredient = ingredientGroup.first()
                RecipeIngredient(
                    ingredient.amount!!,
                    Ingredient(
                        ingredientId!!,
                        ingredient.ingredientName!!,
                        ingredient.ingredientResourceId,
                        this.filter { ingredientId == it.ingredientId }
                            .distinctBy { it.ingredientTagId }.map {
                                Tag(
                                    it.ingredientTagId!!,
                                    it.ingredientTagName!!,
                                    it.ingredientTagResourceId
                                )
                            }
                    )
                )
            },
        instructions = recipeProperties.instructions,
        url = recipeProperties.url,
        imageUrl = recipeProperties.imageUrl,
        tags = this.groupBy { it.recipeTagId }.map { (tagId, tagGroup) ->
            val tag = tagGroup.first()
            Tag(tagId!!, tag.recipeTagName!!, tag.recipeTagResourceId)
        }
    )
}