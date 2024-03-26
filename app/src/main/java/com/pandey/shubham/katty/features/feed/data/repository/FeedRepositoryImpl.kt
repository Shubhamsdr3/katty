package com.pandey.shubham.katty.features.feed.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.core.utils.MAX_CACHED_ITEMS
import com.pandey.shubham.katty.features.detail.data.CatImageResponse
import com.pandey.shubham.katty.features.feed.data.FeedDataSource
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 * TODO: hardcoded exception.
 */

class FeedRepositoryImpl @Inject constructor(
    private val apiService: FeedApiService,
    private val appDatabase: AppDatabase
): FeedRepository {

    override fun getCatImagesPaginated() = Pager(
        config = getDefaultConfig(),
        pagingSourceFactory = { FeedDataSource(apiService, appDatabase) },
    ).flow

    private fun getDefaultConfig() =
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            maxSize = MAX_CACHED_ITEMS,
        )

    override suspend fun updateFavorite(catBreedItemInfo: CatBreedInfoEntity) {
        if (catBreedItemInfo.isFavourite) {
            appDatabase.cateInfoDao().addFavouriteBreed(catBreedItemInfo)
        } else {
            appDatabase.cateInfoDao().removeFavorite(catBreedItemInfo.breedId)
        }
    }

    override fun getFavouriteFromDb(breedId: String?) = appDatabase.cateInfoDao().getFavouriteBreed(breedId)

    override suspend fun getCatBreedDetail(catBreedId: String?): NetworkState<CatBreedItemInfo> {
        return try {
            if (catBreedId.isNullOrBlank()) throw IllegalArgumentException("Invalid id")
            val response = apiService.getCatBreedDetail(catBreedId)
            if (response.isSuccessful) {
                val catBreedItem = response.body()?.toCatBreedItem()
                NetworkState.Success(catBreedItem)
            } else {
                NetworkState.Error(IOException("Something went wrong"))
            }
        } catch (ex: Exception) {
            NetworkState.Error(ex)
        }
    }

    override suspend fun getCatImages(catBreedId: String?): NetworkState<CatImageResponse> {
        return try {
            if (catBreedId.isNullOrBlank()) throw IllegalArgumentException("Invalid id")
            val imageResponse = apiService.getCatImages(catBreedId)
            if (imageResponse.isSuccessful) {
                NetworkState.Success(imageResponse.body())
            } else {
                NetworkState.Error(IOException("Something went wrong"))
            }
        } catch (ex: Exception) {
            NetworkState.Error(ex)
        }
    }
}