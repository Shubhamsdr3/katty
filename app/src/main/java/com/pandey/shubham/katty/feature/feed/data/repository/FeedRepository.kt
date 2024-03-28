package com.pandey.shubham.katty.feature.feed.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.feature.detail.data.CatImageResponse
import com.pandey.shubham.katty.feature.feed.data.dtos.CatDetailItemResponse
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.flow.Flow

/**
 * Created by shubhampandey
 */
interface FeedRepository {

    fun getCatImagesPaginated(): LiveData<PagingData<CatBreedItemInfo>>

    suspend fun updateFavorite(catBreedItemInfo: CatBreedInfoEntity): Number

    fun getFavouriteFromDb(breedId: String?): Flow<CatBreedInfoEntity?>

    suspend fun getCatBreedDetail(catBreedId: String?): NetworkState<CatDetailItemResponse?>

    suspend fun getCatImages(catBreedId: String?): NetworkState<CatImageResponse?>
}