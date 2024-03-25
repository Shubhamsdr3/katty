package com.pandey.shubham.katty.features.feed.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponseItem
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.flow.Flow

/**
 * Created by shubhampandey
 */
interface FeedRepository {

    fun getCatImagesPaginated(): Flow<PagingData<CatBreedItemInfo>>

    suspend fun addFavouriteBreed(catBreedItemInfo: CatBreedInfoEntity)

    fun getFavouriteFromDb(breedId: String?): Flow<CatBreedInfoEntity?>

    suspend fun getCatBreedDetail(catBreedId: String?): NetworkState<CatBreedItemInfo>
}