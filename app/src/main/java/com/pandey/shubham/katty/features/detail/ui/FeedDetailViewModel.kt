package com.pandey.shubham.katty.features.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.core.network.getResult
import com.pandey.shubham.katty.features.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatImageUseCase
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponseItem
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
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
): ViewModel() {

    private val _feedDetailUiState: MutableLiveData<FeedDetailUiState> = MutableLiveData()
    val feedDetailUiState: LiveData<FeedDetailUiState> = _feedDetailUiState

    fun getCateDetail(catBreedId: String?) {
        detailUseCase(catBreedId).onEach { state ->
            when (state) {
                is NetworkState.Loading -> _feedDetailUiState.value = FeedDetailUiState.ShowLoader
                is NetworkState.Error -> _feedDetailUiState.value = FeedDetailUiState.ShowError(state.throwable)
                is NetworkState.Success -> handleResponse(state.data)
            }
        }.launchIn(viewModelScope)
    }

    fun addToFavourite(catBreedItemInfo: CatBreedItemInfo) {
        addFavoriteUseCase(catBreedItemInfo.toBreedInfoEntity(), viewModelScope)
    }

    private fun handleResponse(data: Any?) {
        if (data == null) {
            _feedDetailUiState.value = FeedDetailUiState.ShowError(IOException("Something went wrong"))
            return
        }
        if (data is CatBreedItemInfo) {
            _feedDetailUiState.value = FeedDetailUiState.ShowFeedDetail(data)
        } else if (data is CatBreedResponseItem) {
            _feedDetailUiState.value = FeedDetailUiState.ShowFeedDetail(data.toCatBreedItem())
        }
    }
}