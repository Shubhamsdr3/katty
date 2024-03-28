package com.pandey.shubham.katty.core.di

import com.pandey.shubham.katty.core.FeedApiService
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFeedRepository(apiService: FeedApiService, database: AppDatabase): FeedRepository {
        return FeedRepositoryImpl(apiService, database)
    }
}