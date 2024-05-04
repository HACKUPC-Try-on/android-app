package com.tryon.app.features.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tryon.app.components.ImageContainer
import com.tryon.app.features.dashboard.TryOnStateView
import com.tryon.app.features.dashboard.TryOnViewModel
import com.tryon.theme.TryOnTheme

@Composable
fun TryOnScreen(
    viewModel: TryOnViewModel = hiltViewModel()
) {

    val viewState = viewModel.viewState.collectAsState()

    when (viewState.value) {
        TryOnStateView.Initial -> viewModel.createImageFile()

        is TryOnStateView.Error -> {
            /*Show snackbar error*/
            viewModel.setToInitialState()
        }

        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TryOnTheme.Dimen.Spacing.M),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.Spacing.M)
    ) {
        Text(
            text = "TryOn!",
            style = TryOnTheme.Typography.headline
        )
        ImageContainer(
            modifier = Modifier
                .padding(horizontal = TryOnTheme.Dimen.Spacing.L)
                .fillMaxWidth()
                .height(400.dp),
            image = viewState.value.form.imageUri
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            viewState.value.form.fileUri?.let { fileUri ->
                CameraLauncherComponent(
                    uri = fileUri,
                    onSuccessCamera = { imageUri ->
                        viewModel.onImageUploadSuccess(imageUri)
                    },
                    onFailure = { errorMessage ->
                        viewModel.onImageUploadError(errorMessage)
                    }
                )
                GalleryLauncherComponent(
                    onSuccessGallery = { imageUri ->
                        viewModel.onImageUploadSuccess(imageUri)
                    },
                    onErrorGallery = { errorMessage ->
                        viewModel.onImageUploadError(errorMessage)
                    }
                )
            }
        }
    }
}
