package com.tryon.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tryon.theme.TryOnTheme

@Composable
fun ProgressOverlay() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.Spacing.SM),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Processing recommendations",
            style = TryOnTheme.Typography.subhead
        )
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = TryOnTheme.Colors.highlight,
            trackColor = TryOnTheme.Colors.primary,
        )
    }
}