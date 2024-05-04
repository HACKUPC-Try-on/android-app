package com.tryon.app.features.dashboard.data

import android.net.Uri
import com.tryon.network.DataResponse
import com.tryon.network.DataSource
import com.tryon.network.Mapper
import com.tryon.network.NetworkClient
import com.tryon.network.Predicate

interface GetClothesIDsDataSource {
    suspend fun getClothesIDs(predicate: GetClothesIDsPredicate): DataResponse<List<String>>
}

class GetClothesIDsDataSourceImpl(
    client: NetworkClient
) : GetClothesIDsDataSource,
    DataSource<TryonApi, List<String>, List<String>>(client) {
    override suspend fun getClothesIDs(
        predicate: GetClothesIDsPredicate
    ): DataResponse<List<String>> = retrieveData(predicate)
}

class GetClothesIDsPredicate(
    private val imageUri: Uri,
    private val category: String
) : Predicate<TryonApi, List<String>, List<String>> {
    override fun service(): Class<TryonApi> = TryonApi::class.java

    override fun endpoint(): suspend (TryonApi) -> List<String> = {
        it.getClothesIDs(image = imageUri.toMultipart(), category = category)
    }

    override fun mapper(): Mapper<List<String>, List<String>> = ClothesMapper()
}

class ClothesMapper : Mapper<List<String>, List<String>> {
    override fun toDomainModel(dto: List<String>): List<String> = dto
}
