package com.pandey.shubham.katty.feature.feed.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.utils.ERROR_ADDING_FAVOURITE
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class AddFavoriteUseCase @Inject constructor(
    private val repository: FeedRepository
) : UseCase<CatBreedInfoEntity, Flow<NetworkState<Boolean>>>() {
    override fun run(param: CatBreedInfoEntity?, scope: CoroutineScope) = flow {
        if (param == null) emit(NetworkState.Error(IOException(ERROR_ADDING_FAVOURITE)))
        val result = repository.updateFavorite(param!!)
        if (result.toInt() >= 0) {
            emit(NetworkState.Success(true))
        } else {
            emit(NetworkState.Error(IOException(ERROR_ADDING_FAVOURITE)))
        }
    }
}