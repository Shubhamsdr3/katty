package com.pandey.shubham.katty.feature.detail.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.failure.NoDataAvailableException
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getNetworkResult
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
): UseCase<String, Flow<NetworkState<CatBreedItemInfo>>>() {

    override fun run(param: String?, scope: CoroutineScope) = flow {
        val localData = repository.getFavouriteFromDb(param).first()
        if (localData != null) {
            emit(NetworkState.Success(localData.toCatBreedItem()))
        } else {
            repository.getCatBreedDetail(param).getNetworkResult(
                onSuccess = {
                    if (it != null) {
                        emit(NetworkState.Success(it.toCatBreedItem()))
                    } else {
                        emit(NetworkState.Error(NoDataAvailableException()))
                    }
                },
                onError = {
                    emit(NetworkState.Error(throwable = it))
                }
            )
        }
    }
}