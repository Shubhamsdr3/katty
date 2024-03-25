package com.pandey.shubham.katty.features.detail.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class GetCatDetailUseCase @Inject constructor(
    private val repository: FeedRepository
): UseCase<String, Flow<NetworkState<CatBreedItemInfo>>>() {

    override fun run(param: String?, scope: CoroutineScope) = flow {
        emit(NetworkState.Loading)
        withContext(Dispatchers.IO) {
            val localData = repository.getFavouriteFromDb(param).first()
            if (localData != null) {
                emit(NetworkState.Success(localData.toCatBreedItem()))
            } else {
                emit(repository.getCatBreedDetail(param))
            }
        }
    }
}