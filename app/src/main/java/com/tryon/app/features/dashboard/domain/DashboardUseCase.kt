package com.tryon.app.features.dashboard.domain

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.media3.common.util.Util
import com.tryon.app.features.dashboard.data.GetCategoriesDataSource
import com.tryon.app.features.dashboard.data.GetCategoriesPredicate
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSource
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSourceImpl
import com.tryon.app.features.dashboard.data.GetClothesIDsPredicate
import com.tryon.network.DataResponse
import com.tryon.network.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date

interface DashboardUseCase {
    fun createImageFile(): Uri
    suspend fun getCategoriesFromImage(userImage: Uri): DataResponse<RecommendationsDomainModel>

    suspend fun getClothesForCategory(userImage: Uri, category: String): DataResponse<RecommendationsDomainModel>
}

class DashboardUseCaseImpl(
    private val context: Context,
    private val getCategoriesDataSource: GetCategoriesDataSource,
    private val getClothesIDsDataSource: GetClothesIDsDataSource
) : DashboardUseCase {
    override fun createImageFile(): Uri {
        val file = context.createImageFile()

        return FileProvider.getUriForFile(
            context,
            "com.app.id.fileProvider", file
        )
    }

    override suspend fun getCategoriesFromImage(userImage: Uri): DataResponse<RecommendationsDomainModel> =
        getCategoriesDataSource.getCategories(
            predicate = GetCategoriesPredicate(userImage)
        ).map {
            if (it.size > 1) {
                DataResponse.Success(RecommendationsDomainModel(categories = it))
            } else {
                getClothesForCategory(userImage = userImage, category = it[0].id)
            }
        }

    override suspend fun getClothesForCategory(
        userImage: Uri,
        category: String
    ): DataResponse<RecommendationsDomainModel> =
        getClothesIDsDataSource.getClothesIDs(predicate = GetClothesIDsPredicate(
            userImage, category
        )).map {
            // Make call to retrieve all images
            DataResponse.Success(RecommendationsDomainModel())
        }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir /* directory */
    )
}
