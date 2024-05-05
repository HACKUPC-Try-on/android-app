package com.tryon.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tryon.theme.TryOnTheme

@Composable
fun ProgressOverlay() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.SM),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Processing recommendations",
            style = TryOnTheme.Typography.headline
        )
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = TryOnTheme.Colors.highlight,
            trackColor = TryOnTheme.Colors.primary,
        )
    }
}

@Composable
fun FullScreenProgressOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TryOnTheme.Colors.accent.copy(alpha = 0.8f)),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.SM),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = TryOnTheme.Dimen.spacing.M),
                text = "Hold on... we are designing your new look",
                style = TryOnTheme.Typography.headline,
                textAlign = TextAlign.Center
            )
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = TryOnTheme.Colors.highlight,
                trackColor = TryOnTheme.Colors.primary,
            )
        }
    }
}