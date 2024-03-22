package com.pandey.shubham.katty.feature.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.core.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.IllegalStateException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
@HiltViewModel
class FeedDetailViewModel @Inject constructor(private val feedRepository: FeedRepository): ViewModel() {

    private val _feedDetailUiState: MutableLiveData<FeedDetailUiState> = MutableLiveData()
    val feedDetailUiState: LiveData<FeedDetailUiState> = _feedDetailUiState

    fun getCateDetail(catId: String?) {
        if (catId == null) {
            _feedDetailUiState.value =
                FeedDetailUiState.ShowError(IllegalStateException("Not a valid Id"))
            return
        }
        feedRepository.getCatDetail(catId).onEach { state ->
            when(state) {
                is NetworkState.Loading -> _feedDetailUiState.value = FeedDetailUiState.ShowLoader
                is NetworkState.Error -> _feedDetailUiState.value =
                    FeedDetailUiState.ShowError(state.throwable)
                is NetworkState.Success -> _feedDetailUiState.value =
                    FeedDetailUiState.ShowFeedDetail(state.data)
            }
        }.launchIn(viewModelScope)
    }
}