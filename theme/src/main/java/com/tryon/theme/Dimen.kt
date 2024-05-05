package com.tryon.theme

import androidx.compose.ui.unit.dp

class Spacing {
    val zero = 0.dp
    val XS = 8.dp
    val S = 16.dp
    val SM = 20.dp
    val M = 24.dp
    val L = 40.dp
}

class CornerRadius {
    val card = 16.dp
    val button = 8.dp
}

class Borders {
    val iconButton = 2.dp
}

class Dimen(
    val Spacing: Spacing,
    val CornerRadius: CornerRadius,
    val Borders: Borders
)

fun dimen() = Dimen(
    Spacing = Spacing(),
    CornerRadius = CornerRadius(),
    Borders = Borders()
)