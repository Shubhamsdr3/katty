package com.pandey.shubham.katty.features.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

@HiltViewModel
class HomeFeedViewModel @Inject constructor(private val repository: FeedRepository): ViewModel() {

    fun fetchFeedDataPaginated() = repository.getCateImagesPaginated().cachedIn(viewModelScope).asLiveData()
    fun addToFavourite(favourite: CatBreedItemInfo) {
        viewModelScope.launch {
            repository.addFavourite(favourite.toBreedInfoEntity())
        }
    }
}