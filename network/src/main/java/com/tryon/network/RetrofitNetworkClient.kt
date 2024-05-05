package com.tryon.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitNetworkClient(
    config: NetworkConfig
): NetworkClient {

    private val client = Retrofit.Builder()
        .baseUrl(config.environment.host)
        .addConverterFactory(GsonConverterFactory.create())
        .client(initOkHttpClient(config))
        .build()

    private fun initOkHttpClient(config: NetworkConfig): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val timeout = config.environment.networkTimeout.toLong()

        val builder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)

        config.interceptors.forEach { builder.addInterceptor(it) }

        return builder.build()
    }
    override fun <T> createService(serviceClass: Class<T>): T = client.create(serviceClass)
}

interface NetworkClient {
    fun<T> createService(serviceClass: Class<T>): T
}
