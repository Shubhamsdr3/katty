package com.pandey.shubham.katty.features.feed.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.features.feed.data.FeedDataSource
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.core.utils.MAX_CACHED_ITEMS
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class FeedRepository @Inject constructor(
    private val apiService: FeedApiService,
    private val appDatabase: AppDatabase
) {

    fun getCateImagesPaginated() = Pager(
        config = getDefaultConfig(),
        pagingSourceFactory = { FeedDataSource(apiService, appDatabase) },
    ).flow

    private fun getDefaultConfig() =
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            maxSize = MAX_CACHED_ITEMS,
        )

    suspend fun addFavourite(catBreedItemInfo: CatBreedItemInfo) {
        appDatabase.cateInfoDao().addFavouriteBreed(catBreedItemInfo.toBreedInfoEntity())
    }

    suspend fun getFavouriteFromDb(breedId: String?) = appDatabase.cateInfoDao().getFavouriteBreed(breedId)

    fun getCatBreedDetail(catBreedId: String?) = flow {
        emit(NetworkState.Loading)
        try {
            if (catBreedId.isNullOrBlank()) throw IllegalArgumentException("Invalid id")
            val cateBreedInfo = getFavouriteFromDb(catBreedId)
            if (cateBreedInfo != null) {
                emit(NetworkState.Success(cateBreedInfo.toCatBreedItem()))
            } else {
                val response = apiService.getCatBreedDetail(catBreedId)
                if (response.isSuccessful) {
                    emit(NetworkState.Success(response.body()))
                } else {
                    emit(NetworkState.Error(IOException("Something went wrong")))
                }
            }
        } catch (ex: Exception) {
            emit(NetworkState.Error(ex))
        }
    }
}