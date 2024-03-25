package com.pandey.shubham.katty.features.detail.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.features.detail.data.CatImageResponse
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
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
            emit(feedRepository.getCatImages(param))
        }
    }
}