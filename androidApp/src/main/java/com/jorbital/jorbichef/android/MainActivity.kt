package com.jorbital.jorbichef.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorbital.jorbichef.Greeting
import com.jorbital.jorbichef.android.theme.JorbichefTheme
import com.jorbital.jorbichef.auth.JorbichefAuth
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.repository.TestRepository
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JorbichefTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GreetingView()
                }
            }
        }
    }
}

@Composable
fun GreetingView(
    greeting: Greeting = koinInject(),
    test: TestRepository = koinInject(),
    auth: JorbichefAuth = koinInject()
) {
    val scope = rememberCoroutineScope()
    val tags by test.allTags(scope.coroutineContext).collectAsState(initial = emptyList())
    val ingredients by test.allIngredients(scope.coroutineContext)
        .collectAsState(initial = emptyList())
    val recipes by test.allRecipes(scope.coroutineContext).collectAsState(initial = emptyList())
    val menus by test.allMenus(scope.coroutineContext).collectAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        auth.signInAnonymously()
        test.syncFromApi()
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        GreetingText(greeting.greet())
        tags.forEach { tag ->
            Text(tag.name)
        }
        Spacer(modifier = Modifier.height(8.dp))
        ingredients.forEach { ingredient ->
            Ingredient(ingredient)
        }
        Spacer(modifier = Modifier.height(8.dp))
        recipes.forEach { recipe ->
            Text(recipe.name)
            recipe.tags.forEach {
                Text(it.name)
            }
            recipe.ingredients.forEach { ingredient ->
                Ingredient(ingredient.ingredient, ingredient.amount)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        menus.forEach { menu ->
            Text("${menu.date}")
            menu.recipes.forEach { recipe ->
                Text(recipe.name) }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun Ingredient(ingredient: Ingredient, amount: String? = null) {
    Row {
        amount?.let { Text("$it ") }
        Text("${ingredient.name} - ")
        ingredient.tags.forEach { tag ->
            Text("${tag.name} ")
        }
    }
}

@Composable
fun GreetingText(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    JorbichefTheme {
        GreetingText("Hello, Android!")
    }
}
