package com.tryon.app.features.dashboard.di

import android.content.Context
import com.tryon.app.features.dashboard.FileManager
import com.tryon.app.features.dashboard.data.GetCategoriesDataSource
import com.tryon.app.features.dashboard.data.GetCategoriesDataSourceImpl
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSource
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSourceImpl
import com.tryon.app.features.dashboard.data.GetClothesReplacedDataSource
import com.tryon.app.features.dashboard.data.GetClothesReplacedDataSourceImpl
import com.tryon.app.features.dashboard.data.GetClothingImageDataSource
import com.tryon.app.features.dashboard.data.GetClothingImageDataSourceImpl
import com.tryon.app.features.dashboard.domain.DashboardUseCase
import com.tryon.app.features.dashboard.domain.DashboardUseCaseImpl
import com.tryon.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DashboardModule {

    @Provides
    fun providesGetClothesReplacedDataSource(
        client: NetworkClient
    ): GetClothesReplacedDataSource = GetClothesReplacedDataSourceImpl(client)

    @Provides
    fun providesGetClothesIDsDataSource(
        client: NetworkClient
    ): GetClothesIDsDataSource = GetClothesIDsDataSourceImpl(client)

    @Provides
    fun providesGetCategoriesDataSource(
        client: NetworkClient
    ): GetCategoriesDataSource = GetCategoriesDataSourceImpl(client)

    @Provides
    fun providesGetClothingImageDataSource(
        client: NetworkClient
    ): GetClothingImageDataSource = GetClothingImageDataSourceImpl(client)

    @Provides
    fun providesDashBoardUseCase(
        @ApplicationContext appContext: Context,
        getCategoriesDataSource: GetCategoriesDataSource,
        getClothesIDsDataSource: GetClothesIDsDataSource,
        getClothingImageDataSource: GetClothingImageDataSource,
        getClothesReplacedDataSource: GetClothesReplacedDataSource,
        fileManager: FileManager
    ): DashboardUseCase = DashboardUseCaseImpl(
        context = appContext,
        getCategoriesDataSource = getCategoriesDataSource,
        getClothesIDsDataSource = getClothesIDsDataSource,
        getClothingImageDataSource = getClothingImageDataSource,
        getClothesReplacedDataSource = getClothesReplacedDataSource,
        fileManager = fileManager
    )
}
