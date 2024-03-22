package com.pandey.shubham.katty.feature.detail.ui

import com.pandey.shubham.katty.feature.feed.data.dtos.CatImageResponse

/**
 * Created by shubhampandey
 */
sealed class FeedDetailUiState {

    data object ShowLoader: FeedDetailUiState()

    data class ShowError(val throwable: Throwable?): FeedDetailUiState()

    data class ShowFeedDetail(val feedItem: CatImageResponse?): FeedDetailUiState()
}