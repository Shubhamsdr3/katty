package com.pandey.shubham.katty.feature.feed.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.pandey.shubham.katty.core.FeedApiService
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.failure.InvalidIdException
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getNetworkResult
import com.pandey.shubham.katty.core.network.makeRequest
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.core.utils.MAX_CACHED_ITEMS
import com.pandey.shubham.katty.feature.detail.data.CatImageResponse
import com.pandey.shubham.katty.feature.feed.data.FeedDataSource
import com.pandey.shubham.katty.feature.feed.data.dtos.CatBreedResponseItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withTimeout
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

private const val TAG = "FeedRepositoryImpl"

class FeedRepositoryImpl @Inject constructor(
    private val apiService: FeedApiService,
    private val appDatabase: AppDatabase
): FeedRepository {

    private val defaultConfig get() = PagingConfig(
        pageSize = DEFAULT_PAGE_SIZE,
        maxSize = MAX_CACHED_ITEMS,
        enablePlaceholders = false
    )
    private val pager
        get() = Pager(
            config = defaultConfig,
            pagingSourceFactory = { FeedDataSource(apiService, appDatabase) },
        )

    override fun getCatImagesPaginated() = pager.liveData

    override suspend fun updateFavorite(catBreedItemInfo: CatBreedInfoEntity) {
        if (catBreedItemInfo.isFavourite) {
            appDatabase.cateInfoDao().addFavouriteBreed(catBreedItemInfo)
        } else {
            appDatabase.cateInfoDao().removeFavorite(catBreedItemInfo.breedId)
        }
    }

    override fun getFavouriteFromDb(breedId: String?) = appDatabase.cateInfoDao().getFavouriteBreed(breedId)

    override suspend fun getCatBreedDetail(catBreedId: String?): NetworkState<CatBreedResponseItem?> {
        return try {
            if (catBreedId.isNullOrBlank()) throw InvalidIdException()
            makeRequest(IO) { apiService.getCatBreedDetail(catBreedId) }
        } catch (ex: Exception) {
            NetworkState.Error(throwable = ex)
        }
    }

    override suspend fun getCatImages(catBreedId: String?): NetworkState<CatImageResponse?> {
        return try {
            if (catBreedId.isNullOrBlank()) throw InvalidIdException()
            makeRequest(IO) { apiService.getCatImages(catBreedId) }
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
            NetworkState.Error(throwable = ex)
        }
    }
}