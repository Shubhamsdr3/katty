package com.pandey.shubham.katty.features.detail.di

import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatImageUseCase
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
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
    fun provideCatDetailUseCase(feedRepository: FeedRepository): GetCatImageUseCase {
        return GetCatImageUseCase(feedRepository)
    }
}