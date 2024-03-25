package com.pandey.shubham.katty.core.di

import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.feed.domain.usecase.GetCatDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun provideFeedRepository(apiService: FeedApiService, database: AppDatabase): FeedRepository {
        return FeedRepositoryImpl(apiService, database)
    }

    @Provides
    fun provideCatDetailUseCase(feedRepository: FeedRepository): GetCatDetailUseCase {
        return GetCatDetailUseCase(feedRepository)
    }

    @Provides
    fun provideAddFavoriteUseCase(feedRepository: FeedRepository): AddFavoriteUseCase {
        return AddFavoriteUseCase(feedRepository)
    }
}