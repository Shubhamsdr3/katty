package com.pandey.shubham.katty.features.feed.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.pandey.shubham.katty.core.base.UseCase
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class GetFeedDataUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) : UseCase<Unit, LiveData<PagingData<CatBreedItemInfo>>>() {
    override fun run(param: Unit?, scope: CoroutineScope) = feedRepository.getCatImagesPaginated()
}