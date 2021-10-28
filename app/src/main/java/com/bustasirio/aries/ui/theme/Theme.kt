package com.bustasirio.aries.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = PrimaryDarker,
    secondary = Accent,
    secondaryVariant = AccentDarker,

    background = DarkBackground,
    surface = DarkDarkerBackground,
    onPrimary = DarkText,
    onSecondary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,

    error = DarkError,
    onError = DarkText
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = PrimaryDarker,
    secondary = Accent,
    secondaryVariant = AccentDarker,

    background = LightBackground,
    surface = LightDarkerBackground,
    onPrimary = LightText,
    onSecondary = LightText,
    onBackground = LightText,
    onSurface = LightText,

    error = LightError,
    onError = LightText
)

@Composable
fun AriesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}