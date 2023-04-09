package com.jorbital.jorbichef.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.jorbital.jorbichef.Greeting
import com.jorbital.jorbichef.android.theme.JorbichefTheme
import com.jorbital.jorbichef.auth.JorbichefAuth
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
    LaunchedEffect(Unit) {
        auth.signInAnonymously()
        test.syncFromApi()
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        GreetingText(greeting.greet())
        tags.forEach { tag ->
            Text(tag.name)
        }
        ingredients.forEach { ingredient ->
            Row {
                Text("${ingredient.name} - ")
                ingredient.tags.forEach { tag ->
                    Text("${tag.name} ")
                }
            }

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
