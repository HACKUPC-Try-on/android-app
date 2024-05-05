package com.tryon.app.features.dashboard.data

import android.content.Context
import okhttp3.ResponseBody
import retrofit2.Response
import android.net.Uri
import com.tryon.app.features.dashboard.FileManager
import com.tryon.app.features.dashboard.toMultipart
import com.tryon.network.DataResponse
import com.tryon.network.DataSource
import com.tryon.network.Mapper
import com.tryon.network.NetworkClient
import com.tryon.network.Predicate

interface GetClothesReplacedDataSource {
    suspend fun getClothesReplaced(predicate: GetClothesReplacedPredicate): DataResponse<Uri>
}

class GetClothesReplacedDataSourceImpl(
    client: NetworkClient
) : GetClothesReplacedDataSource,
    DataSource<TryonApi, Uri, Response<ResponseBody>>(client) {
    override suspend fun getClothesReplaced(
        predicate: GetClothesReplacedPredicate
    ): DataResponse<Uri> = retrieveData(predicate)
}

class GetClothesReplacedPredicate(
    private val userImage: Uri,
    private val clothingImage: Uri,
    private val fileManager: FileManager,
    private val context: Context
) : Predicate<TryonApi, Uri, Response<ResponseBody>> {
    override fun service(): Class<TryonApi> = TryonApi::class.java

    override fun endpoint(): suspend (TryonApi) -> Response<ResponseBody> = {
        it.getClothesReplaced(
            human = userImage.toMultipart(context, "human"),
            cloth = clothingImage.toMultipart(context, "cloth")
        )
    }

    override fun mapper(): Mapper<Uri, Response<ResponseBody>> = ByteArrayToUriMapper(fileManager)
}
