package com.tryon.app.features.dashboard.di

import android.content.Context
import com.tryon.app.features.dashboard.domain.DashboardUseCase
import com.tryon.app.features.dashboard.domain.DashboardUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DashboardModule {

  @Provides
  fun providesDashBoardUseCase(
      @ApplicationContext appContext: Context,
  ): DashboardUseCase = DashboardUseCaseImpl(
      context = appContext
  )
}
