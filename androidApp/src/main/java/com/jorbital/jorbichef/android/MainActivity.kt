package com.jorbital.jorbichef.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorbital.jorbichef.Greeting
import com.jorbital.jorbichef.android.theme.JorbichefTheme
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
fun GreetingView(greeting: Greeting = koinInject()) {
    GreetingText(greeting.greet())
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
