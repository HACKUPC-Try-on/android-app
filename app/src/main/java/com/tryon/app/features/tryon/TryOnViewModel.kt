package com.tryon.app.features.tryon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TryOnViewModel @Inject constructor(): ViewModel() {

}

sealed class TryOnStateView {

}