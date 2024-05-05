package com.tryon.app.features.dashboard.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tryon.app.components.ImageContainer
import com.tryon.app.components.ProgressOverlay
import com.tryon.app.components.ShowSnackBar
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

        is DashboardViewState.Loading -> {
            ProgressOverlay()
        }

        is DashboardViewState.Error -> {
           ShowSnackBar(message = (viewState.value as DashboardViewState.Error).message) {
               viewModel.setToInitialState()
           }
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
            onClickRecommendation = { viewModel.replaceUserImageWithNewClothing(it) }
        )
    }
}

@Composable
fun RecommendationsComponent(
    recommendationsViewState: RecommendationsViewState,
    onClickCategory: (ClothingCategories) -> Unit,
    onClickRecommendation: (Int) -> Unit
) {
    when (recommendationsViewState) {
        RecommendationsViewState.Loading -> {
            ProgressOverlay()
        }

        is RecommendationsViewState.Active -> {
            if (recommendationsViewState.form.recommendations.categories.isNotEmpty()) {
                CategoriesList(
                    categories = recommendationsViewState.form.recommendations.categories,
                    onClickCategory = onClickCategory
                )
            } else if (recommendationsViewState.form.recommendations.clothes.isNotEmpty()) {
                ClothesList(
                    clothes = recommendationsViewState.form.recommendations.clothesBitmap,
                    onClickImage = {
                        onClickRecommendation(it)
                    }
                )
            }
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
    modifier: Modifier = Modifier,
    clothes: List<Bitmap>,
    onClickImage: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(clothes) { index, image ->
            Image(
                modifier = Modifier
                    .size(width = 350.dp, height = 400.dp)
                    .clickable(role = Role.Button, onClick = { onClickImage(index) }),
                bitmap = image.asImageBitmap(), contentDescription = null
            )
        }
    }
}
