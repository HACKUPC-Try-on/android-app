package com.tryon.app.features.dashboard.di

import android.content.Context
import com.tryon.app.features.dashboard.data.GetCategoriesDataSource
import com.tryon.app.features.dashboard.data.GetCategoriesDataSourceImpl
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSource
import com.tryon.app.features.dashboard.data.GetClothesIDsDataSourceImpl
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
    fun providesGetClothesIDsDataSource(
        client: NetworkClient
    ): GetClothesIDsDataSource = GetClothesIDsDataSourceImpl(client)

    @Provides
    fun providesGetCategoriesDataSource(
        client: NetworkClient
    ): GetCategoriesDataSource = GetCategoriesDataSourceImpl(client)

  @Provides
  fun providesDashBoardUseCase(
      @ApplicationContext appContext: Context,
      getCategoriesDataSource: GetCategoriesDataSource
  ): DashboardUseCase = DashboardUseCaseImpl(
      context = appContext,
      getCategoriesDataSource = getCategoriesDataSource
  )
}
