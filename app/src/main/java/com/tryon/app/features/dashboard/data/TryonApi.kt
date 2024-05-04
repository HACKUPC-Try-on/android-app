package com.tryon.app.features.dashboard.data

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface TryonApi {

    @Multipart
    @POST("categories/")
    fun getCategories(
        @Part image: MultipartBody.Part
    ): List<String>

    @Multipart
    @POST("recommendations/")
    fun getClothesIDs(
        @Part image: MultipartBody.Part,
        @Query("category") category: String
    ): List<String>

    @GET("image/")
    fun getImageBytes(
        @Query("image_id") imageId: String
    )
}
