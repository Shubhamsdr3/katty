package com.pandey.shubham.katty.feature.feed.ui

import com.pandey.shubham.katty.core.database.CatBreedInfoEntity

/**
 * Created by shubhampandey
 */
sealed class HomeUiState {

    data class OnFavoriteEvent(val catBreedItemEntity: CatBreedInfoEntity?): HomeUiState()
}