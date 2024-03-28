package com.pandey.shubham.katty.feature.feed.ui

import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.failure.model.ErrorMessage

/**
 * Created by shubhampandey
 */
sealed class HomeUiState {
    data class ShowLoader(val isLoading: Boolean): HomeUiState()
    data class ShowError(val error: ErrorMessage): HomeUiState()
    data object OnNotLoading: HomeUiState()
    data class UpdateFavorite(val catBreedItemEntity: CatBreedInfoEntity?): HomeUiState()
    data class OnFavouriteUpdated(val isSuccess: Boolean, val error: String?): HomeUiState()
}