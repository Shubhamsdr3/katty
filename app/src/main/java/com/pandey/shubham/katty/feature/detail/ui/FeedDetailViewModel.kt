package com.pandey.shubham.katty.feature.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.feature.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.feature.detail.domain.usecase.GetCatDetailUseCase
import com.pandey.shubham.katty.feature.detail.ui.FeedItemDetailFragment.Companion.CAT_BREED_ID
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.domain.usecase.AddFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 *
 * TODO:
 * 1. Get images for current breed.
 *
 */
@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val detailUseCase: GetCatDetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _feedDetailUiState: MutableLiveData<FeedDetailUiState> = MutableLiveData()
    val feedDetailUiState: LiveData<FeedDetailUiState> = _feedDetailUiState

    init {
        val catBreedId = savedStateHandle.get<String>(CAT_BREED_ID)
        getCateDetail(catBreedId)
    }

    fun getCateDetail(catBreedId: String?) {
        _feedDetailUiState.value = FeedDetailUiState.ShowLoader
        detailUseCase(catBreedId).onEach { state ->
            when (state) {
                is NetworkState.Error -> _feedDetailUiState.value = FeedDetailUiState.ShowError(state.throwable)
                is NetworkState.Success -> handleResponse(state.data)
            }
        }.launchIn(viewModelScope)
    }

    fun addToFavourite(catBreedItemInfo: CatBreedItemInfo) {
        addFavoriteUseCase(catBreedItemInfo.toBreedInfoEntity(), viewModelScope)
    }

    private fun handleResponse(data: CatDetailInfo?) {
        if (data != null) {
            _feedDetailUiState.value = FeedDetailUiState.ShowFeedDetail(data)
        } else {
            _feedDetailUiState.value = FeedDetailUiState.ShowError(IOException("Something went wrong"))
        }
    }
}