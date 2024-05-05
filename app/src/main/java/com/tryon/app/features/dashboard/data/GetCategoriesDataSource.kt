package com.tryon.app.features.dashboard.data

import android.content.Context
import android.net.Uri
import com.tryon.app.features.dashboard.domain.ClothingCategories
import com.tryon.app.features.dashboard.toMultipart
import com.tryon.network.DataResponse
import com.tryon.network.DataSource
import com.tryon.network.Mapper
import com.tryon.network.NetworkClient
import com.tryon.network.Predicate
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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
    private val userImage: Uri,
    private val context: Context
) : Predicate<TryonApi, List<ClothingCategories>, List<String>> {
    override fun service(): Class<TryonApi> = TryonApi::class.java

    override fun endpoint(): suspend (TryonApi) -> List<String> = {
        it.getCategories(image = userImage.toMultipart(context = context, "image"))
    }

    override fun mapper(): Mapper<List<ClothingCategories>, List<String>> = CategoriesMapper()
}

class CategoriesMapper : Mapper<List<ClothingCategories>, List<String>> {
    override fun toDomainModel(dto: List<String>): List<ClothingCategories> = dto.map {
        ClothingCategories.getCategoryFromCode(it)
    }
}
