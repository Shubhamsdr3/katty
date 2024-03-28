package com.pandey.shubham.katty.features.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.pandey.shubham.katty.BaseViewModelTest
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.failure.model.ErrorMessage
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight
import com.pandey.shubham.katty.feature.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.feature.feed.domain.usecase.GetFavouriteUseCase
import com.pandey.shubham.katty.feature.feed.domain.usecase.GetFeedDataUseCase
import com.pandey.shubham.katty.feature.feed.ui.HomeFeedViewModel
import com.pandey.shubham.katty.feature.feed.ui.HomeUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shubhampandey
 * Test:
 *     1. loading/error/success state.
 *     2. Show data from cache in case network unavailable.
 *     3. Refresh state(from pull or option menu)
 *     4. Error when there is no data in cache and network error.
 *     5. When add to favourite fails.
 */

@RunWith(MockitoJUnitRunner::class)
class HomeFeedViewModelTest: BaseViewModelTest() {

    private val getFeedDataUseCase: GetFeedDataUseCase = mockk(relaxed = true)

    private val addFavoriteUseCase: AddFavoriteUseCase = mockk()

    private val getFavouriteUseCase: GetFavouriteUseCase = mockk(relaxed = true)

    private val repository: FeedRepository = mockk(relaxed = true)

    private lateinit var viewModel: HomeFeedViewModel

    private val catBreeds = listOf(getMockCatInfo())

    private val pagingData: LiveData<PagingData<CatBreedItemInfo>> = MutableLiveData(PagingData.from(catBreeds))

    @Before
    fun setUp() {
        every { getFeedDataUseCase.invoke(null) } returns pagingData
        viewModel = HomeFeedViewModel(getFeedDataUseCase, addFavoriteUseCase, getFavouriteUseCase)
    }

    @Test
    fun `show loader on loading states`() = runTest {
        viewModel.onLoadingStateUpdate(getLoadState(LoadState.Loading))
        val uiState = viewModel.homeUiState.value
        val isLoading = uiState is HomeUiState.ShowLoader && uiState.isLoading
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `when failure hide loading and show error`() = runTest {
        val error = "An error occurred"
        val errorMessage = ErrorMessage(errorMessage = error)
        viewModel.onLoadingStateUpdate(getLoadState(LoadState.Error(Throwable(error))))
        val uiState = viewModel.homeUiState.value
        assertThat(uiState is HomeUiState.ShowLoader).isFalse()
        val errorResponse = if(uiState is HomeUiState.ShowError) uiState.error else null
        assertThat(errorResponse).isEqualTo(errorMessage)
    }

    @Test
    fun `when success hide loading and error`() = runTest {
        viewModel.onLoadingStateUpdate(getLoadState(LoadState.NotLoading(true)))
        val uiState = viewModel.homeUiState.value
        assertThat(uiState is HomeUiState.ShowLoader).isFalse()
        assertThat(uiState is HomeUiState.ShowError).isFalse()
    }

    @Test
    fun `when add to favorite fails`() = runTest {
        val catBreedInfoItem = getMockCatInfoEntity()
        coEvery { repository.updateFavorite(catBreedInfoItem) }.returns(-1)
        val addFavoriteUseCase: AddFavoriteUseCase = mockk(relaxed = true)
        addFavoriteUseCase()
        val uiState = viewModel.homeUiState.value
        assertThat(uiState is HomeUiState.OnFavouriteUpdated && uiState.isSuccess).isFalse()
    }


    private fun getMockCatInfo(): CatBreedItemInfo {
        return  CatBreedItemInfo(
            breedId = "abys",
            imageUrl = "",
            name = "Abyssinian",
            weight = Weight(imperial = "7  -  10", metric = "3 - 5"),
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            origin = "Egypt",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            lifeSpan = "14 - 15",
            isFavourite = true
        )
    }

    private fun getMockCatInfoEntity(): CatBreedInfoEntity {
        return  CatBreedInfoEntity(
            breedId = "abys",
            imageUrl = "",
            name = "Abyssinian",
            weight = Weight(imperial = "7  -  10", metric = "3 - 5"),
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            origin = "Egypt",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            lifeSpan = "14 - 15",
            createdAt = System.currentTimeMillis(),
            isFavourite = true
        )
    }

    private fun getLoadState(state: LoadState): CombinedLoadStates =
        CombinedLoadStates(state, state, state, LoadStates(state, state, state))
}