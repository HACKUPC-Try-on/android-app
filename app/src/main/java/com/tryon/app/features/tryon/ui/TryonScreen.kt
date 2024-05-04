package com.tryon.app.features.tryon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tryon.app.features.tryon.TryOnViewModel
import com.tryon.theme.TryOnTheme

@Composable
fun TryOnScreen(
    viewModel: TryOnViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TryOnTheme.Dimen.Spacing.M),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.Spacing.S)
    ) {
        Text(
            text = "TryOn!",
            style = TryOnTheme.Typography.headline
        )
    }
}