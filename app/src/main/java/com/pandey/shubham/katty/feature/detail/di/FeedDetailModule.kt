package com.pandey.shubham.katty.feature.detail.di

import com.pandey.shubham.katty.feature.detail.domain.usecase.GetCatDetailUseCase
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */

@Module
@InstallIn(SingletonComponent::class)
class FeedDetailModule {

    @Provides
    fun provideCatDetailUseCase(feedRepository: FeedRepository): GetCatDetailUseCase {
        return GetCatDetailUseCase(feedRepository)
    }
}