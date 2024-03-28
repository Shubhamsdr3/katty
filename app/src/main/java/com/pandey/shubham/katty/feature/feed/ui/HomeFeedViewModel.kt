package com.pandey.shubham.katty.feature.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pandey.shubham.katty.core.failure.model.ErrorMessage
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.feature.feed.domain.usecase.GetFavouriteUseCase
import com.pandey.shubham.katty.feature.feed.domain.usecase.GetFeedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        addFavoriteUseCase(favourite.toBreedInfoEntity()).onEach { state ->
            when(state) {
                is NetworkState.Success -> {
                    _homeUiState.value = HomeUiState.OnFavouriteUpdated(true, null)
                }
                is NetworkState.Error -> {
                    _homeUiState.value = HomeUiState.OnFavouriteUpdated(false, state.throwable.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFavoriteBreed(breedId: String) {
        viewModelScope.launch {
            getFavouriteUseCase(breedId).collectLatest {
                _homeUiState.value = HomeUiState.UpdateFavorite(it)
            }
        }
    }

    fun onLoadingStateUpdate(loadStates: CombinedLoadStates) {
        when(loadStates.refresh) {
            is LoadState.Loading -> _homeUiState.value = HomeUiState.ShowLoader(true)
            is LoadState.Error -> _homeUiState.value = HomeUiState.ShowError(ErrorMessage(errorMessage = (loadStates.refresh as LoadState.Error).error.message))
            is LoadState.NotLoading -> _homeUiState.value = HomeUiState.OnNotLoading
        }
    }
}