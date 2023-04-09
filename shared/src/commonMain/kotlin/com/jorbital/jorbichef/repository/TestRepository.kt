package com.jorbital.jorbichef.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jorbital.jorbichef.Database
import com.jorbital.jorbichef.api.FirestoreApi
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.models.Tag
import com.jorbital.jorbichef.models.firestore.IngredientDocument
import com.jorbital.jorbichef.models.firestore.TagDocument
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class TestRepository(private val db: Database, private val api: FirestoreApi) {

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
    }

    fun allTags(context: CoroutineContext) =
        db.tagQueries.selectAll().asFlow().mapToList(context).map {
            it.map { tag ->
                Tag(
                    id = tag.id,
                    name = tag.name,
                    resourceId = tag.resourceId
                )
            }
        }

    fun allIngredients(context: CoroutineContext) =
        db.ingredientQueries.selectAll().asFlow().mapToList(context).map { queryList ->
            val ingredients = mutableSetOf<Ingredient>()
            queryList.forEach { queryObject ->
                if (ingredients.find { it.id == queryObject.id } == null) {
                    ingredients.add(
                        Ingredient(
                            id = queryObject.id,
                            name = queryObject.name,
                            resourceId = queryObject.resourceId,
                            tags = queryList.filter {
                                it.id == queryObject.id && it.tagId != null && it.tagName != null
                            }.map {
                                Tag(
                                    id = it.tagId!!,
                                    name = it.tagName!!,
                                    resourceId = it.tagResourceId
                                )
                            }
                        )
                    )
                }
            }
            ingredients.toList()
        }
}