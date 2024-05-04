package com.tryon.app.features.dashboard

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.tryon.app.features.dashboard.domain.DashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TryOnViewModel @Inject constructor(
    val dashboardUseCase: DashboardUseCase
): ViewModel() {
    private val _viewState: MutableStateFlow<TryOnStateView> = MutableStateFlow(
        TryOnStateView.Initial
    )

    val viewState: StateFlow<TryOnStateView> get() = _viewState

    fun createImageFile() {
        _viewState.value = TryOnStateView.Active(
            form = viewState.value.form.copy(
                fileUri = dashboardUseCase.createImageFile()
            )
        )
    }

    fun onImageUploadSuccess(imageUri: Uri) {
        _viewState.value = TryOnStateView.Active(
            form = viewState.value.form.copy(
                imageUri = imageUri
            )
        )
    }

    fun onImageUploadError(e: String) {

    }

    fun setToInitialState() {
        _viewState.value = TryOnStateView.Initial
    }

}

data class TryOnFormState(
    val fileUri: Uri? = null,
    val imageUri: Uri? = null
)
sealed class TryOnStateView(
    val form: TryOnFormState = TryOnFormState()
) {
    data object Initial: TryOnStateView(form = TryOnFormState())

    class Active(
        form: TryOnFormState
    ) : TryOnStateView(form = form)

    class Error(
        form: TryOnFormState,
        val e: String? = null
    ): TryOnStateView(form = form)
}
