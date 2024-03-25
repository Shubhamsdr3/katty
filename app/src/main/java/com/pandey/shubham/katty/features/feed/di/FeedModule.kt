package com.pandey.shubham.katty.features.feed.di

import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */

@Module
@InstallIn(SingletonComponent::class)
class FeedModule {

    @Provides
    fun provideCatDetailUseCase(feedRepository: FeedRepository): GetCatDetailUseCase {
        return GetCatDetailUseCase(feedRepository)
    }

    @Provides
    fun provideAddFavoriteUseCase(feedRepository: FeedRepository): AddFavoriteUseCase {
        return AddFavoriteUseCase(feedRepository)
    }
}