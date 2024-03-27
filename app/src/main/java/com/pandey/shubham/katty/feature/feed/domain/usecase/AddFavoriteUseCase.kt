package com.pandey.shubham.katty.feature.feed.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class AddFavoriteUseCase @Inject constructor (private val repository: FeedRepository): UseCase<CatBreedInfoEntity, Unit>() {
    override fun run(param: CatBreedInfoEntity?, scope: CoroutineScope) {
        scope.launch {
            param?.let {
                repository.updateFavorite(param)
            }
        }
    }
}