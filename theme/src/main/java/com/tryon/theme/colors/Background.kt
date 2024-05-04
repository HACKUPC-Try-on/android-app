package com.tryon.theme.colors

import androidx.compose.ui.graphics.Color

data class Background(
    val default: Color,
    val secondary: Color
)

fun background(darkTheme: Boolean) = if (darkTheme) darkBackground else lightBackground

private val lightBackground = Background(
    default = Purple40,
    secondary = PurpleGrey40
)

private val darkBackground = Background(
    default = Purple80,
    secondary = PurpleGrey80
)
