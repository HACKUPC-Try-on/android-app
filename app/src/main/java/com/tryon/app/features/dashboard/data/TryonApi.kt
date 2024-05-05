package com.tryon.app.features.dashboard.data

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface TryonApi {

    @Multipart
    @POST("categories")
    suspend fun getCategories(
        @Part image: MultipartBody.Part
    ): List<String>

    @Multipart
    @POST("recommendations")
    suspend fun getClothesIDs(
        @Part image: MultipartBody.Part,
        @Query("category") category: String
    ): List<String>

    @GET("image")
    suspend fun getImageBytes(
        @Query("image_id") imageId: String
    ): Response<ResponseBody>

    @Multipart
    @POST("tryon")
    suspend fun getClothesReplaced(
        @Part human: MultipartBody.Part,
        @Part cloth: MultipartBody.Part
    ): Response<ResponseBody>
}
