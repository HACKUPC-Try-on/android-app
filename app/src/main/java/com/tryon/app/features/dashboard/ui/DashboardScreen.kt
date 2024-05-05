package com.tryon.app.features.dashboard.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tryon.app.components.FullScreenProgressOverlay
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
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TryOnTheme.Dimen.spacing.M)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.M)
    ) {
        Text(
            text = "TryOn!",
            style = TryOnTheme.Typography.headline,
            fontSize = 32.sp
        )
        ImageContainer(
            modifier = Modifier
                .padding(horizontal = TryOnTheme.Dimen.spacing.L)
                .wrapContentSize(),
            image = viewState.value.form.imageBitmap
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            viewState.value.form.fileUri?.let { fileUri ->
                CameraLauncherComponent(
                    uri = fileUri,
                    isEnabled = recommendationsViewState.value !is RecommendationsViewState.Loading,
                    onSuccessCamera = { imageUri ->
                        viewModel.onImageUploadSuccess(imageUri)
                    }
                )
            }
            GalleryLauncherComponent(
                onSuccessGallery = { imageUri ->
                    viewModel.onImageUploadSuccess(imageUri)
                },
                isEnabled = recommendationsViewState.value !is RecommendationsViewState.Loading,
            )
        }
        RecommendationsComponent(
            recommendationsViewState = recommendationsViewState.value,
            onClickCategory = { viewModel.getClothesFromCategory(it) },
            onClickRecommendation = { viewModel.replaceUserImageWithNewClothing(it) }
        )
    }
    when (viewState.value) {
        is DashboardViewState.Loading -> {
            FullScreenProgressOverlay()
        }

        is DashboardViewState.Error -> {
            ShowSnackBar(message = (viewState.value as DashboardViewState.Error).message) {
                viewModel.setToInitialState()
            }
        }

        else -> {}
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
    Column(
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.M)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Which type of clothing do you want to get recommendations of?",
            style = TryOnTheme.Typography.subhead,
            textAlign = TextAlign.Center
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { category ->
                CategoryRowItem(category = category) {
                    onClickCategory(category)
                }
            }
        }
    }
}

@Composable
fun CategoryRowItem(
    category: ClothingCategories,
    onClickCategory: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = TryOnTheme.Dimen.borders.categoryContainer,
                color = TryOnTheme.Colors.tertiary,
                shape = RectangleShape
            )
            .clickable(
                role = Role.Button,
                onClick = onClickCategory
            )
    ) {
        Row(
            modifier = Modifier.padding(all = TryOnTheme.Dimen.spacing.XS),
            horizontalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.XS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.id,
                style = TryOnTheme.Typography.button
            )
            Icon(painter = painterResource(id = category.iconRes), contentDescription = null)
        }
    }
}

@Composable
fun ClothesList(
    modifier: Modifier = Modifier,
    clothes: List<Bitmap>,
    onClickImage: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.spacing.S),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "These are the clothes we recommend to you!",
            style = TryOnTheme.Typography.subhead
        )
        Text(
            modifier = Modifier.padding(bottom = TryOnTheme.Dimen.spacing.XS),
            text = "You can click on one of them to try it out",
            style = TryOnTheme.Typography.body
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
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
}
