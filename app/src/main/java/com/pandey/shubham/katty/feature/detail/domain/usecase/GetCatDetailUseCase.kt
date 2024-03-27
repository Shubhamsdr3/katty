package com.pandey.shubham.katty.feature.detail.domain.usecase

import android.util.Log
import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.failure.NoDataAvailableException
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getNetworkResult
import com.pandey.shubham.katty.feature.detail.data.CatImageResponseItem
import com.pandey.shubham.katty.feature.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class GetCatDetailUseCase @Inject constructor(
    private val repository: FeedRepository
): UseCase<String, Flow<NetworkState<CatDetailInfo>>>() {

    override fun run(param: String?, scope: CoroutineScope) = flow {
        val imageResponse = repository.getCatImages(param)
        val images = if (imageResponse is NetworkState.Success) {
            imageResponse.data
        } else {
            null
        }
        Log.d("FeedRepositoryImpl", "The image response: ${imageResponse}")
        val localData = repository.getFavouriteFromDb(param).first()
        if (localData != null) {
            val cateDetail = CatDetailInfo(localData.toCatBreedItem(), images)
            emit(NetworkState.Success(cateDetail))
        } else {
            val detailResponse = repository.getCatBreedDetail(param)
            val detailInfo = if(detailResponse is NetworkState.Success) {
                detailResponse.data
            } else {
                null
            }
            if (detailInfo != null) {
                val catDetailInfo = CatDetailInfo(detailInfo.toCatBreedItem(), images)
                emit(NetworkState.Success(catDetailInfo))
            } else {
                emit(NetworkState.Error(NoDataAvailableException()))
            }
        }
    }
}