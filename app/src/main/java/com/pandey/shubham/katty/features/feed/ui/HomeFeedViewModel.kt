package com.pandey.shubham.katty.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.feed.domain.usecase.GetFavouriteUseCase
import com.pandey.shubham.katty.features.feed.domain.usecase.GetFeedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    getFeedDataUseCase: GetFeedDataUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavouriteUseCase: GetFavouriteUseCase
): ViewModel() {

    private val _homeUiState: MutableLiveData<HomeUiState> = MutableLiveData()
    val homeUiState: LiveData<HomeUiState> = _homeUiState

    val fetchFeedDataPaginated = getFeedDataUseCase.invoke().cachedIn(viewModelScope)

    fun addToFavourite(favourite: CatBreedItemInfo) {
        addFavoriteUseCase(favourite.toBreedInfoEntity())
    }

    fun getFavoriteBreed(breedId: String) {
        viewModelScope.launch {
            getFavouriteUseCase(breedId).collectLatest {
                _homeUiState.value = HomeUiState.OnFavoriteEvent(it)
            }
        }
    }
}