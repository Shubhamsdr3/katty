package com.pandey.shubham.katty.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

@HiltViewModel
class HomeFeedViewModel @Inject constructor(private val repository: FeedRepositoryImpl): ViewModel() {

    private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData()
    val homeUiState: LiveData<HomeUiState> = _homeUiState
    fun fetchFeedDataPaginated() = repository.getCatImagesPaginated().cachedIn(viewModelScope).asLiveData()
    fun addToFavourite(favourite: CatBreedItemInfo) {
        viewModelScope.launch {
            repository.updateFavorite(favourite.toBreedInfoEntity())
        }
    }

    fun getFavoriteBreed(breedId: String) {
        viewModelScope.launch {
            repository.getFavouriteFromDb(breedId).collectLatest {
                _homeUiState.value = HomeUiState.OnFavoriteEvent(it)
            }
        }
    }
}