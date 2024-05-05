package com.tryon.app.features.dashboard.domain

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.tryon.app.features.dashboard.FileManager
import com.tryon.app.features.dashboard.data.GetCategoriesDataSource
import com.tryon.app.features.dashboard.data.GetCategoriesPredicate
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSource
import com.tryon.app.features.dashboard.data.GetClothesIDsPredicate
import com.tryon.app.features.dashboard.data.GetClothesReplacedDataSource
import com.tryon.app.features.dashboard.data.GetClothesReplacedPredicate
import com.tryon.app.features.dashboard.data.GetClothingImageDataSource
import com.tryon.app.features.dashboard.data.GetClothingImagePredicate
import com.tryon.network.DataResponse
import com.tryon.network.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

interface DashboardUseCase {
    fun getImageFileForCamera(): Uri?
    suspend fun getCategoriesFromImage(userImage: Uri): DataResponse<RecommendationsDomainModel>

    suspend fun getClothesForCategory(
        userImage: Uri,
        category: String
    ): DataResponse<RecommendationsDomainModel>

    suspend fun replaceUserImageClothes(
        userImage: Uri,
        clothingImage: Uri
    ): DataResponse<Uri>

    suspend fun convertImagesToBitmaps(
        images: List<Uri>
    ): List<Bitmap>
}

class DashboardUseCaseImpl(
    private val context: Context,
    private val getCategoriesDataSource: GetCategoriesDataSource,
    private val getClothesIDsDataSource: GetClothesIDsDataSource,
    private val getClothingImageDataSource: GetClothingImageDataSource,
    private val getClothesReplacedDataSource: GetClothesReplacedDataSource,
    private val fileManager: FileManager
) : DashboardUseCase {
    override fun getImageFileForCamera(): Uri = fileManager.createImageFile()

    override suspend fun getCategoriesFromImage(userImage: Uri): DataResponse<RecommendationsDomainModel> =
        getCategoriesDataSource.getCategories(
            predicate = GetCategoriesPredicate(userImage, context)
        ).map {
            Log.d("GetCategoriesFromImage: ", "${it.size}")
            if (it.size > 1) {
                DataResponse.Success(RecommendationsDomainModel(categories = it))
            } else {
                getClothesForCategory(userImage = userImage, category = it[0].id)
            }
        }

    override suspend fun getClothesForCategory(
        userImage: Uri,
        category: String
    ): DataResponse<RecommendationsDomainModel> = withContext(Dispatchers.IO) {
        getClothesIDsDataSource.getClothesIDs(
            predicate = GetClothesIDsPredicate(
                userImage, category, context
            )
        ).map {
            val imagesCall = it.map {
                async {
                    getClothingImageDataSource.getClothingImage(
                        predicate = GetClothingImagePredicate(it, fileManager)
                    )
                }
            }
            val results = imagesCall.awaitAll()
            val images: MutableList<Uri> = mutableListOf<Uri>().apply {
                results.map { response ->
                    if (response is DataResponse.Success) {
                        this.add(response.data)
                    }
                }
            }
            DataResponse.Success(
                RecommendationsDomainModel(
                    clothes = images,
                    clothesBitmap = images.map {
                        fileManager.uriToBitmap(it)
                    }
                )
            )
        }
    }

    override suspend fun replaceUserImageClothes(
        userImage: Uri,
        clothingImage: Uri
    ): DataResponse<Uri> =
        getClothesReplacedDataSource.getClothesReplaced(
            predicate = GetClothesReplacedPredicate(
                userImage = userImage,
                clothingImage = clothingImage,
                fileManager = fileManager,
                context = context
            )
        )

    override suspend fun convertImagesToBitmaps(images: List<Uri>): List<Bitmap> = images.map {
        fileManager.uriToBitmap(it)
    }
}
