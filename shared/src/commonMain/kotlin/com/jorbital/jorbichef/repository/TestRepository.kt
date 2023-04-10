package com.jorbital.jorbichef.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jorbital.jorbichef.Database
import com.jorbital.jorbichef.Platform
import com.jorbital.jorbichef.api.FirestoreApi
import com.jorbital.jorbichef.database.toRecipe
import com.jorbital.jorbichef.database.toRecipeParts
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.models.Menu
import com.jorbital.jorbichef.models.Tag
import com.jorbital.jorbichef.models.firestore.IngredientDocument
import com.jorbital.jorbichef.models.firestore.TagDocument
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlin.coroutines.CoroutineContext

class TestRepository(
    private val platform: Platform,
    val db: Database,
    private val api: FirestoreApi
) {

    suspend fun syncFromApi() {
        val apiTags = api.getTags()
        apiTags.forEach {
            when (it) {
                is TagDocument.Default -> db.tagQueries.insertOrReplace(
                    id = it.id,
                    name = it.name,
                    resourceId = it.resourceId
                )

                is TagDocument.Custom -> db.tagQueries.insertOrReplace(
                    id = it.id,
                    name = it.name,
                    resourceId = null
                )
            }
        }
        val apiIngredients = api.getIngredients()
        apiIngredients.forEach {
            when (it) {
                is IngredientDocument.Default -> {
                    db.ingredientQueries.insertOrReplace(
                        id = it.id,
                        name = it.name,
                        resourceId = it.resourceId
                    )
                    it.tagIds.forEach { tag ->
                        db.ingredientQueries.connectTag(
                            ingredientId = it.id,
                            tagId = tag
                        )
                    }
                }

                is IngredientDocument.Custom -> {
                    db.ingredientQueries.insertOrReplace(
                        id = it.id,
                        name = it.name,
                        resourceId = null
                    )
                    it.tagIds.forEach { tag ->
                        db.ingredientQueries.connectTag(
                            ingredientId = it.id,
                            tagId = tag
                        )
                    }
                }
            }
        }
        val recipes = api.getRecipes()
        recipes.forEach { pair ->
            val recipe = pair.first
            val ingredients = pair.second
            db.recipeQueries.insertOrReplace(
                id = recipe.id,
                name = recipe.name,
                instructions = recipe.instructions,
                url = recipe.url,
                imageUrl = recipe.imageUrl
            )
            ingredients.forEach { ingredient ->
                db.recipeQueries.connectIngredient(
                    recipeId = recipe.id,
                    ingredientId = ingredient.id,
                    amount = ingredient.amount
                )
            }
            recipe.tagIds.forEach { tag ->
                db.recipeQueries.connectTag(
                    recipeId = recipe.id,
                    tagId = tag
                )
            }
        }
        val menus = api.getMenus()
        menus.forEach {
            db.menuQueries.insertOrReplace(
                id = it.id,
                date = it.date,
                recipeId = it.recipeId
            )
        }
    }

    fun allTags(context: CoroutineContext) =
        db.tagQueries.selectAllTags().asFlow().mapToList(context).map {
            it.map { tag ->
                Tag(
                    id = tag.id,
                    name = tag.name,
                    resourceId = tag.resourceId
                )
            }
        }

    fun allIngredients(context: CoroutineContext) =
        db.ingredientQueries.selectAllIngredients().asFlow().mapToList(context).map { queryList ->
            val ingredients = queryList.groupBy { it.id }.map { (ingredientId, ingredientGroup) ->
                val ingredientProperties = ingredientGroup.first()
                val tags = ingredientGroup.filter {
                    it.tagId != null && it.tagName != null
                }.map {
                    Tag(
                        id = it.tagId!!,
                        name = it.tagName!!,
                        resourceId = it.tagResourceId
                    )
                }
                Ingredient(
                    id = ingredientId,
                    name = ingredientProperties.name,
                    resourceId = ingredientProperties.resourceId,
                    tags = tags
                )
            }
            ingredients
        }

    fun allRecipes(context: CoroutineContext) =
        db.recipeQueries.selectAllRecipes().asFlow().mapToList(context).map { queryList ->
            val recipes = queryList.groupBy { it.id }.map { (_, recipeGroup) ->
                recipeGroup.map { it.toRecipeParts() }.toRecipe()
            }
            recipes
        }

    fun allMenus(context: CoroutineContext) =
        db.menuQueries.selectAllMenus().asFlow().mapToList(context).map { queryList ->
            val menus = queryList.groupBy { it.date }.map { (_, menuGroup) ->
                val menu = menuGroup.first()
                Menu(
                    id = menu.id,
                    date = LocalDate.parse(menu.date),
                    recipes = menuGroup.groupBy { it.date }.map { (_, recipeGroup) ->
                        recipeGroup.map { it.toRecipeParts() }.toRecipe()
                    }
                )
            }
            menus
        }
}