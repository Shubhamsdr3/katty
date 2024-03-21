package com.pandey.shubham.katty.detail.ui

import com.pandey.shubham.katty.feed.data.FeedItem

/**
 * Created by shubhampandey
 */
sealed class FeedDetailUiState {

    data object ShowLoader: FeedDetailUiState()

    data class ShowError(val throwable: Throwable?): FeedDetailUiState()

    data class ShowFeedDetail(val feedItem: FeedItem?): FeedDetailUiState()
}