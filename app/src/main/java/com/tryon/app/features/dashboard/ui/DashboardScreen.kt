package com.tryon.app.features.dashboard.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tryon.app.components.ImageContainer
import com.tryon.app.features.dashboard.DashboardViewState
import com.tryon.app.features.dashboard.RecommendationsViewState
import com.tryon.app.features.dashboard.TryOnViewModel
import com.tryon.app.features.dashboard.domain.ClothingCategories
import com.tryon.theme.TryOnTheme

@Composable
fun TryOnScreen(
    viewModel: TryOnViewModel = hiltViewModel()
) {

    val viewState = viewModel.dashboardViewState.collectAsState()
    val recommendationsViewState = viewModel.recommendationsViewState.collectAsState()

    when (viewState.value) {
        DashboardViewState.Initial -> viewModel.createImageFile()

        is DashboardViewState.Error -> {
            /*Show snackbar error*/
        }

        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TryOnTheme.Dimen.Spacing.M)
            .verticalScroll(rememberScrollState()),
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
            }
            GalleryLauncherComponent(
                onSuccessGallery = { imageUri ->
                    viewModel.onImageUploadSuccess(imageUri)
                },
                onErrorGallery = { errorMessage ->
                    viewModel.onImageUploadError(errorMessage)
                }
            )
        }
        RecommendationsComponent(
            recommendationsViewState = recommendationsViewState.value,
            onClickCategory = { viewModel.getClothesFromCategory(it) },
            onClickRecommendation = {}
        )
    }
}

@Composable
fun RecommendationsComponent(
    recommendationsViewState: RecommendationsViewState,
    onClickCategory: (ClothingCategories) -> Unit,
    onClickRecommendation: (Uri) -> Unit
) {
    when (recommendationsViewState) {
        RecommendationsViewState.Loading -> {
            // Progress Overlay
        }

        is RecommendationsViewState.Active -> {
            if (recommendationsViewState.form.recommendations.categories.isNotEmpty()) {
                CategoriesList(
                    categories = recommendationsViewState.form.recommendations.categories,
                    onClickCategory = onClickCategory
                )
            } else if (recommendationsViewState.form.recommendations.clothes.isNotEmpty()) {
                ClothesList(clothes = recommendationsViewState.form.recommendations.clothes)
            }
        }

        is RecommendationsViewState.Error -> {
            /*Show snackbar error*/
        }

        else -> {}
    }
}

@Composable
fun CategoriesList(
    categories: List<ClothingCategories>,
    onClickCategory: (ClothingCategories) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.Spacing.S)
    ) {
        categories.forEach {
            Text(
                modifier = Modifier.clickable(
                    role = Role.Button,
                    onClick = { onClickCategory(it) }),
                text = it.id
            )
        }
    }
}

@Composable
fun ClothesList(
    clothes: List<Uri>
) {

}
