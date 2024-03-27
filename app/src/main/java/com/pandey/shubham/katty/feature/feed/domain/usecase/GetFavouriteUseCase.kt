package com.pandey.shubham.katty.feature.feed.domain.usecase

import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class GetFavouriteUseCase @Inject constructor(
    private val repository: FeedRepository
) : UseCase<String, Flow<CatBreedInfoEntity?>>() {
    override fun run(param: String?, scope: CoroutineScope): Flow<CatBreedInfoEntity?> = repository.getFavouriteFromDb(param)
}