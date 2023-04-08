package com.jorbital.jorbichef.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorbital.jorbichef.Greeting
import com.jorbital.jorbichef.android.theme.JorbichefTheme
import com.jorbital.jorbichef.api.FirestoreApi
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
fun GreetingView(greeting: Greeting = koinInject(), test: TestRepository = koinInject(), auth: JorbichefAuth = koinInject(), api: FirestoreApi = koinInject()) {
    var ingredients by remember { mutableStateOf(emptyList<Ingredient>()) }
    LaunchedEffect(Unit){
        auth.signInAnonymously()
        api.generateDefaultData()
        ingredients = test.getIngredients()
    }
    Column(){
        GreetingText(greeting.greet())
        ingredients.forEach { ingredient ->
            Text(ingredient.name)
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
