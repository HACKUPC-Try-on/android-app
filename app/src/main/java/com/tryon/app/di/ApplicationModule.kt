package com.tryon.app.di

import com.tryon.app.BuildConfig
import com.tryon.network.Environment
import com.tryon.network.NetworkClient
import com.tryon.network.NetworkConfig
import com.tryon.network.RetrofitNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient = RetrofitNetworkClient(
        NetworkConfig(
            environment = Environment(
                host = BuildConfig.API_HOST,
                forceHttps = BuildConfig.FORCE_HTTPS,
                networkTimeout = BuildConfig.API_TIMEOUT
            ),
            interceptors = emptyList()
        )
    )
}
