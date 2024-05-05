package com.tryon.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tryon.theme.TryOnTheme

@Composable
fun ShowSnackBar(
    message: String,
    onSnackBarFinish: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = message)
    {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message)
            onSnackBarFinish()
        }
    }

    SnackbarHost(hostState = snackbarHostState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Text(
                modifier = Modifier
                    .padding(TryOnTheme.Dimen.Spacing.XS)
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
                    .graphicsLayer {
                        shadowElevation = 5f
                    }
                    .background(color = TryOnTheme.Colors.tertiary)
                    .padding(vertical = 10.dp)
                    .align(Alignment.BottomCenter),
                text = message,
                color = TryOnTheme.Colors.secondary,
                style = TryOnTheme.Typography.body,
                textAlign = TextAlign.Center
            )
        }
    }
}