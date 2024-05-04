package com.tryon.app.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import com.tryon.theme.TryOnTheme

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = TryOnTheme.Dimen.Borders.iconButton,
                shape = CircleShape,
                color = TryOnTheme.Colors.tertiary
            )
            .clip(shape = CircleShape)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(all = TryOnTheme.Dimen.Spacing.S)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
    }
}