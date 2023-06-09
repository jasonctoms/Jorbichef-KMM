package com.jorbital.jorbichef.api

import com.jorbital.jorbichef.auth.JorbichefAuth
import com.jorbital.jorbichef.models.firestore.IngredientDocument
import com.jorbital.jorbichef.models.firestore.RecipeDocument
import com.jorbital.jorbichef.models.firestore.RecipeIngredientDocument
import com.jorbital.jorbichef.models.firestore.TagDocument
import com.jorbital.jorbichef.models.firestore.MenuDocument
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.LocalDate

class FirestoreApi(private val auth: JorbichefAuth) {
    private val firestore by lazy { Firebase.firestore }

    suspend fun getTags(): List<TagDocument> {
        val defaultTags = firestore.collection(DEFAULT_TAGS).get().documents.map {
            it.data(strategy = TagDocument.serializer())
        }
        val customTags = firestore.collection(USERS).document(auth.assertUserId())
            .collection(TAGS).get().documents.map {
                it.data(strategy = TagDocument.serializer())
            }
        return defaultTags + customTags
    }

    suspend fun getIngredients(): List<IngredientDocument> {
        val defaultIngredients = firestore.collection(DEFAULT_INGREDIENTS).get().documents.map {
            it.data(strategy = IngredientDocument.serializer())
        }
        val customIngredients = firestore.collection(USERS).document(auth.assertUserId())
            .collection(INGREDIENTS).get().documents.map {
                it.data(strategy = IngredientDocument.serializer())
            }
        return defaultIngredients + customIngredients
    }

    suspend fun getRecipes(): List<Pair<RecipeDocument, List<RecipeIngredientDocument>>> {
        val userId = auth.assertUserId()
        val recipes = firestore.collection(USERS).document(userId)
            .collection(RECIPES).get().documents.map {
                it.data(strategy = RecipeDocument.serializer())
            }
        return recipes.map { recipe ->
            val ingredients = firestore.collection(USERS).document(userId)
                .collection(RECIPES).document(recipe.id).collection(INGREDIENTS).get()
                .documents.map {
                    it.data(strategy = RecipeIngredientDocument.serializer())
                }
            recipe to ingredients
        }
    }

    suspend fun getMenus(): List<MenuDocument> {
        val userId = auth.assertUserId()
        return firestore.collection(USERS).document(userId)
            .collection(MENU).get().documents.map {
                it.data(strategy = MenuDocument.serializer())
            }
    }

    suspend fun addCustomTag(tag: TagDocument.Custom) {
        firestore.collection(USERS).document(tag.userId).collection(TAGS)
            .document(tag.id)
            .set(strategy = TagDocument.serializer(), data = tag, encodeDefaults = false)
    }

    suspend fun addCustomIngredient(ingredient: IngredientDocument.Custom) {
        firestore.collection(USERS).document(ingredient.userId).collection(INGREDIENTS)
            .document(ingredient.id)
            .set(
                strategy = IngredientDocument.serializer(),
                data = ingredient,
                encodeDefaults = false
            )
    }

    suspend fun addRecipe(recipe: RecipeDocument, ingredients: List<RecipeIngredientDocument>) {
        firestore.collection(USERS).document(recipe.userId).collection(RECIPES)
            .document(recipe.id)
            .set(
                strategy = RecipeDocument.serializer(),
                data = recipe
            )
        ingredients.forEach {
            firestore.collection(USERS).document(recipe.userId).collection(RECIPES)
                .document(recipe.id).collection(INGREDIENTS).document(it.id).set(
                    strategy = RecipeIngredientDocument.serializer(),
                    data = it
                )
        }
    }

    suspend fun addRecipeToMenu(menu: MenuDocument) {
        firestore.collection(USERS).document(menu.userId).collection(MENU)
            .document(menu.id)
            .set(
                strategy = MenuDocument.serializer(),
                data = menu
            )
    }

    suspend fun generateDefaultData() {
        defaultTags.forEach {
            firestore.collection(DEFAULT_TAGS).document(it.id)
                .set(TagDocument.serializer(), it, encodeDefaults = false)
        }
        defaultIngredients.forEach {
            firestore.collection(DEFAULT_INGREDIENTS).document(it.id)
                .set(IngredientDocument.serializer(), it, encodeDefaults = false)
        }
        writeTestData()
    }

    suspend fun writeTestData() {
        addCustomTag(
            TagDocument.Custom(
                id = "favorite",
                name = "Favorite",
                userId = auth.assertUserId()
            )
        )
        addCustomIngredient(
            IngredientDocument.Custom(
                id = "fried-tofu",
                name = "Fried Tofu",
                userId = auth.assertUserId(),
                tagIds = listOf("favorite", "protein", "vegan")
            )
        )
        addRecipe(
            RecipeDocument(
                id = "demo-salad",
                name = "Demo Salad",
                userId = auth.assertUserId(),
                instructions = "Demo instructions",
                url = "https://www.google.com",
                imageUrl = null,
                tagIds = listOf("favorite", "vegan"),
            ),
            listOf(
                RecipeIngredientDocument(
                    id = "lettuce",
                    amount = "1 head"
                ),
                RecipeIngredientDocument(
                    id = "tomato",
                    amount = "1"
                ),
                RecipeIngredientDocument(
                    id = "fried-tofu",
                    amount = "1 package"
                )
            )
        )
        addRecipeToMenu(
            MenuDocument(
                id = "0",
                userId = auth.assertUserId(),
                date = LocalDate(2023, 4, 11).toString(),
                recipeId = "demo-salad",
            )
        )
        addRecipeToMenu(
            MenuDocument(
                id = "1",
                userId = auth.assertUserId(),
                date = LocalDate(2023, 4, 11).toString(),
                recipeId = "demo-salad",
            )
        )
        addRecipeToMenu(
            MenuDocument(
                id = "2",
                userId = auth.assertUserId(),
                date = LocalDate(2023, 4, 12).toString(),
                recipeId = "demo-salad",
            )
        )
    }

    companion object {
        private const val DEFAULT_TAGS = "defaultTags"
        private const val DEFAULT_INGREDIENTS = "defaultIngredients"
        private const val USERS = "users"
        private const val TAGS = "tags"
        private const val INGREDIENTS = "ingredients"
        private const val RECIPES = "recipes"
        private const val MENU = "menu"
    }
}

