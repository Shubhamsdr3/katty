package com.pandey.shubham.katty.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandey.shubham.katty.feed.data.FeedRepository
import com.pandey.shubham.katty.network.HomeFeedUiState
import com.pandey.shubham.katty.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

@HiltViewModel
class HomeFeedViewModel @Inject constructor(private val repository: FeedRepository): ViewModel() {

    private val _homeFeedUiMutableState: MutableLiveData<HomeFeedUiState> = MutableLiveData()
    val homeFeedUiState: LiveData<HomeFeedUiState> = _homeFeedUiMutableState

    fun fetchFeedData() {
        repository.getCatImages().onEach { state ->
            when(state) {
                is NetworkState.Loading -> _homeFeedUiMutableState.value = HomeFeedUiState.ShowLoader
                is NetworkState.Success -> _homeFeedUiMutableState.value = HomeFeedUiState.ShowFeedData(state.data)
                is NetworkState.Error -> _homeFeedUiMutableState.value = HomeFeedUiState.ShowError(state.throwable)
            }
        }.launchIn(viewModelScope)
    }
}