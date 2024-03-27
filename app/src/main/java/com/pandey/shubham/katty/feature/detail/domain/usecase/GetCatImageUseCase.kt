package com.pandey.shubham.katty.feature.detail.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getNetworkResult
import com.pandey.shubham.katty.feature.detail.data.CatImageResponse
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class GetCatImageUseCase @Inject constructor(
    private val feedRepository: FeedRepository
): UseCase<String, Flow<NetworkState<CatImageResponse>>>() {
    override fun run(param: String?, scope: CoroutineScope) = flow {
        scope.launch {
            feedRepository.getCatImages(param).getNetworkResult(
                onSuccess = {
                   emit(NetworkState.Success(it))
                },
                onError = {
                    emit(NetworkState.Error(it))
                }
            )
        }
    }
}