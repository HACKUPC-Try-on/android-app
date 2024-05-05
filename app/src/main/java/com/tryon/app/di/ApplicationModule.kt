package com.tryon.app.di

import android.content.Context
import com.tryon.app.BuildConfig
import com.tryon.app.features.dashboard.FileManager
import com.tryon.app.features.dashboard.FileManagerImpl
import com.tryon.network.Environment
import com.tryon.network.NetworkClient
import com.tryon.network.NetworkConfig
import com.tryon.network.RetrofitNetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
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

    @Provides
    fun provideFileManager(
        @ApplicationContext appContext: Context,
    ): FileManager = FileManagerImpl(
        context = appContext
    )
}
