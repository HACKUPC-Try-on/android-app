package com.tryon.app.features.dashboard.data

import android.net.Uri
import com.tryon.app.features.dashboard.FileManager
import com.tryon.network.DataResponse
import com.tryon.network.DataSource
import com.tryon.network.Mapper
import com.tryon.network.NetworkClient
import com.tryon.network.Predicate
import okhttp3.ResponseBody
import retrofit2.Response

interface GetClothingImageDataSource {
    suspend fun getClothingImage(predicate: GetClothingImagePredicate): DataResponse<Uri>
}

class GetClothingImageDataSourceImpl(
    client: NetworkClient
) : GetClothingImageDataSource,
    DataSource<TryonApi, Uri, Response<ResponseBody>>(client) {
    override suspend fun getClothingImage(
        predicate: GetClothingImagePredicate
    ): DataResponse<Uri> = retrieveData(predicate)
}

class GetClothingImagePredicate(
    private val imageId: String,
    private val fileManager: FileManager
) : Predicate<TryonApi, Uri, Response<ResponseBody>> {
    override fun service(): Class<TryonApi> = TryonApi::class.java

    override fun endpoint(): suspend (TryonApi) -> Response<ResponseBody> = {
        it.getImageBytes(imageId = imageId)
    }

    override fun mapper(): Mapper<Uri, Response<ResponseBody>> = ByteArrayToUriMapper(fileManager)
}

class ByteArrayToUriMapper(
    private val fileManager: FileManager
) : Mapper<Uri, Response<ResponseBody>> {
    override fun toDomainModel(dto: Response<ResponseBody>): Uri {
        dto.body()?.bytes()?.let {
            fileManager.byteArrayToUri(it)?.let { uri ->
                return uri
            }
        } ?: throw Throwable("Couldn't convert image")
    }
}
