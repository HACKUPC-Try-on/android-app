package com.tryon.app.features.dashboard.data

import android.net.Uri
import com.tryon.app.features.dashboard.domain.ClothingCategories
import com.tryon.network.DataResponse
import com.tryon.network.DataSource
import com.tryon.network.Mapper
import com.tryon.network.NetworkClient
import com.tryon.network.Predicate
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

interface GetCategoriesDataSource {
    suspend fun getCategories(predicate: GetCategoriesPredicate): DataResponse<List<ClothingCategories>>
}

class GetCategoriesDataSourceImpl(
    client: NetworkClient
) : GetCategoriesDataSource,
    DataSource<TryonApi, List<ClothingCategories>, List<String>>(client) {
    override suspend fun getCategories(
        predicate: GetCategoriesPredicate
    ): DataResponse<List<ClothingCategories>> = retrieveData(predicate)

}

class GetCategoriesPredicate(
    private val userImage: Uri
) : Predicate<TryonApi, List<ClothingCategories>, List<String>> {
    override fun service(): Class<TryonApi> = TryonApi::class.java

    override fun endpoint(): suspend (TryonApi) -> List<String> = {
        it.getCategories(image = userImage.toMultipart())
    }

    override fun mapper(): Mapper<List<ClothingCategories>, List<String>> = CategoriesMapper()
}

class CategoriesMapper : Mapper<List<ClothingCategories>, List<String>> {
    override fun toDomainModel(dto: List<String>): List<ClothingCategories> = dto.map {
        ClothingCategories.getCategoryFromCode(it)
    }
}

fun Uri.toMultipart(): MultipartBody.Part {
    this.path?.let {
        val file = File(it)
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name = "image", file.name, requestBody)
    }
    throw Exception(message = "Could not send image, missing path")
}