package com.jorbital.jorbichef.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun JorbichefTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        lightColors
    } else {
        darkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}