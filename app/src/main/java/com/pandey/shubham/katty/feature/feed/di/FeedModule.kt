package com.pandey.shubham.katty.feature.feed.di

import com.pandey.shubham.katty.feature.detail.domain.usecase.GetCatDetailUseCase
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.feature.feed.domain.usecase.AddFavoriteUseCase
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