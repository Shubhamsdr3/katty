package com.pandey.shubham.katty.network

import com.pandey.shubham.katty.feed.data.FeedResponse

/**
 * Created by shubhampandey
 */

//TODO: implement sealed interface
sealed class HomeFeedUiState  {

    data object ShowLoader: HomeFeedUiState()

    data class ShowError(val throwable: Throwable?): HomeFeedUiState()

    data class ShowFeedData(val feedResponse: FeedResponse?): HomeFeedUiState()
}