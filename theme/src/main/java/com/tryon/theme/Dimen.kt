package com.tryon.theme

import androidx.compose.ui.unit.dp

class Spacing {
    val Zero = 0.dp
    val S = 16.dp
    val M = 24.dp
    val L = 40.dp
}

class CornerRadius {
    val Card = 16.dp
    val Button = 8.dp
}

class Dimen(
    Spacing: Spacing,
    CornerRadius: CornerRadius
)

fun dimen() = Dimen(
    Spacing = Spacing(),
    CornerRadius = CornerRadius()
)