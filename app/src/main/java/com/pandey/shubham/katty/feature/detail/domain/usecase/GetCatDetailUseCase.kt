package com.pandey.shubham.katty.feature.detail.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.failure.NoDataAvailableException
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getNetworkResult
import com.pandey.shubham.katty.feature.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
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
        val localData = repository.getFavouriteFromDb(param).first()
        if (localData != null) {
            val cateDetail = CatDetailInfo(localData.toCatBreedItem(), images)
            emit(NetworkState.Success(cateDetail))
        } else {
            repository.getCatBreedDetail(param).getNetworkResult(
                onSuccess = { response ->
                    val catDetailInfo = CatDetailInfo(response?.toCatBreedItem(), images)
                    emit(NetworkState.Success(catDetailInfo))
                },
                onError = {
                    emit(NetworkState.Error(it))
                }
            )
        }
    }
}