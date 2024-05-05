package com.tryon.theme

import androidx.compose.ui.unit.dp

class Spacing {
    val XS = 8.dp
    val S = 16.dp
    val SM = 20.dp
    val M = 24.dp
    val L = 40.dp
}

class CornerRadius {
    val card = 16.dp
}

class Borders {
    val iconButton = 2.dp
    val categoryContainer = 2.dp
}

class Dimen(
    val spacing: Spacing,
    val cornerRadius: CornerRadius,
    val borders: Borders
)

fun dimen() = Dimen(
    spacing = Spacing(),
    cornerRadius = CornerRadius(),
    borders = Borders()
)