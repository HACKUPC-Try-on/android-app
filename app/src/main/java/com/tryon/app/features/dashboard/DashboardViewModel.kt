package com.tryon.app.features.dashboard

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryon.app.features.dashboard.domain.ClothingCategories
import com.tryon.app.features.dashboard.domain.DashboardUseCase
import com.tryon.app.features.dashboard.domain.RecommendationsDomainModel
import com.tryon.network.parseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TryOnViewModel @Inject constructor(
    private val dashboardUseCase: DashboardUseCase
): ViewModel() {

    private val _dashboardViewState: MutableStateFlow<DashboardViewState> = MutableStateFlow(
        DashboardViewState.Initial
    )
    val dashboardViewState: StateFlow<DashboardViewState> get() = _dashboardViewState

    private val _recommendationsViewState: MutableStateFlow<RecommendationsViewState> = MutableStateFlow(
        RecommendationsViewState.Initial
    )
    val recommendationsViewState: StateFlow<RecommendationsViewState> get() = _recommendationsViewState

    fun createImageFile() {
        _dashboardViewState.value = DashboardViewState.Active(
            form = dashboardViewState.value.form.copy(
                fileUri = dashboardUseCase.createImageFile()
            )
        )
    }

    fun onImageUploadSuccess(imageUri: Uri) {
        _dashboardViewState.value = DashboardViewState.Active(
            form = dashboardViewState.value.form.copy(
                imageUri = imageUri
            )
        )
        _recommendationsViewState.value = RecommendationsViewState.Loading
        viewModelScope.launch {
            dashboardUseCase.getCategoriesFromImage(userImage = imageUri).parseResult(
                dataSuccess = ::onGetRecommendationsSuccess,
                dataError = ::onGetRecommendationsError
            )
        }
    }

    fun getClothesFromCategory(category: ClothingCategories) {
        dashboardViewState.value.form.imageUri?.let {
            _recommendationsViewState.value = RecommendationsViewState.Loading
            viewModelScope.launch {
                dashboardUseCase.getClothesForCategory(
                    userImage = it,
                    category = category.id
                ).parseResult(
                    dataSuccess = ::onGetRecommendationsSuccess,
                    dataError = ::onGetRecommendationsError
                )
            }
        }
    }

    private fun onGetRecommendationsSuccess(recommendations: RecommendationsDomainModel) {
        _recommendationsViewState.value = RecommendationsViewState.Active(
            form = recommendationsViewState.value.form.copy(
                recommendations = recommendations
            )
        )
    }

    private fun onGetRecommendationsError(error: Throwable) {
        _dashboardViewState.value = DashboardViewState.Error(
            e = "Couldn't fetch recommendations from server",
            form = dashboardViewState.value.form
        )
    }

    fun onImageUploadError(e: String) {

    }

    fun setToInitialState() {
        _dashboardViewState.value = DashboardViewState.Initial
    }
}

data class DashboardFormState(
    val fileUri: Uri? = null,
    val imageUri: Uri? = null
)

sealed class DashboardViewState(
    val form: DashboardFormState = DashboardFormState()
) {
    data object Initial: DashboardViewState(form = DashboardFormState())

    class Active(
        form: DashboardFormState
    ) : DashboardViewState(form = form)

    class Error(
        form: DashboardFormState,
        val e: String? = null
    ): DashboardViewState(form = form)
}

data class RecommendationsFormState(
    val recommendations: RecommendationsDomainModel = RecommendationsDomainModel()
)

sealed class RecommendationsViewState(
    val form: RecommendationsFormState = RecommendationsFormState()
) {
    data object Initial : RecommendationsViewState()
    data object Loading : RecommendationsViewState()

    class Active(
        form: RecommendationsFormState
    ): RecommendationsViewState(form = form)

    class Error(
        e: String? = null,
        form: RecommendationsFormState
    ): RecommendationsViewState(form = form)
}
