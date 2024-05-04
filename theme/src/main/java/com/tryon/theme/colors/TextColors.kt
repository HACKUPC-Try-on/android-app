package com.tryon.theme.colors

import androidx.compose.ui.graphics.Color

data class TextColors(
    val default: Color,
    val soft: Color,
    val error: Color
)

fun textColors(darkTheme: Boolean) = if (darkTheme) lightTextColors else darkTextColors

private val lightTextColors = TextColors(
    default = Purple40,
    soft = PurpleGrey40,
    error = PurpleGrey40
)

private val darkTextColors = TextColors(
    default = Purple80,
    soft = PurpleGrey80,
    error = PurpleGrey40
)
