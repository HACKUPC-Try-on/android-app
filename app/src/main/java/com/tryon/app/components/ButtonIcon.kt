package com.tryon.app.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tryon.theme.TryOnTheme

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int
) {
    Box(
        modifier = modifier
            .border(
                width = TryOnTheme.Dimen.Borders.iconButton,
                shape = CircleShape,
                color = TryOnTheme.Colors.tertiary
            )
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
    }
}