package com.pandey.shubham.katty.features.detail.ui

import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo

/**
 * Created by shubhampandey
 */
sealed class FeedDetailUiState {

    data object ShowLoader: FeedDetailUiState()

    data class ShowError(val throwable: Throwable?): FeedDetailUiState()

    data class ShowFeedDetail(val feedItem: CatBreedItemInfo?): FeedDetailUiState()
}