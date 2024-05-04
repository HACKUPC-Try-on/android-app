package com.tryon.app.features.dashboard.domain

import android.net.Uri

class RecommendationsDomainModel(
    val categories: List<ClothingCategories> = emptyList(),
    val clothes: List<Uri> = emptyList()
)