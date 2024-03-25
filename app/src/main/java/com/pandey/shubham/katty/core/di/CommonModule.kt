package com.pandey.shubham.katty.core.di

import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
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
}