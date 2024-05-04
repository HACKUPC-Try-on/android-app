package com.tryon.theme.colors

import androidx.compose.ui.graphics.Color

val LightGrey10 = Color(0xFFE0E0E0)
val LightGrey = Color(0xFFF0F0F0)
val LighterGrey = Color(0xFF2C2C2C)
val BrightGrey = Color(0xFF373737)
val DarkGrey10 = Color(0xFF121212)
val DarkGrey = Color(0xFF444444)
val Charcoal = Color(0xFF303030)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFB00020)

class Colors (
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val highlight: Color,
    val accent: Color,
    val error: Color
)

fun colors(darkTheme: Boolean) = if (darkTheme) darkColors else lightColors

private val lightColors = Colors(
    primary = LightGrey,
    secondary = Black,
    tertiary = DarkGrey,
    highlight = Charcoal,
    accent = White,
    error = Red
)

private val darkColors = Colors(
    primary = DarkGrey10,
    secondary = LightGrey10,
    tertiary = LighterGrey,
    highlight = BrightGrey,
    accent = White,
    error = Red
)
