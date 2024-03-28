package com.pandey.shubham.katty.features.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.pandey.shubham.katty.BaseViewModelTest
import com.pandey.shubham.katty.core.FeedApiService
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.feature.detail.domain.model.CatDetailInfo
import com.pandey.shubham.katty.feature.detail.domain.usecase.GetCatDetailUseCase
import com.pandey.shubham.katty.feature.detail.ui.FeedDetailUiState
import com.pandey.shubham.katty.feature.detail.ui.FeedDetailViewModel
import com.pandey.shubham.katty.feature.detail.ui.FeedItemDetailFragment.Companion.CAT_BREED_ID
import com.pandey.shubham.katty.feature.feed.data.dtos.CatDetailItemResponse
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.feature.feed.domain.usecase.AddFavoriteUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by shubhampandey
 */
class FeedDetailViewModelTest: BaseViewModelTest() {

    private lateinit var viewModel: FeedDetailViewModel

    private val detailUseCase: GetCatDetailUseCase = mockk()

    private val addFavoriteUseCase: AddFavoriteUseCase = mockk(relaxed = true)

    private val feedApiService: FeedApiService = mockk(relaxed = true)

    private val detailResponse: Response<CatDetailItemResponse> = mockk(relaxed = true)

    private val observer: Observer<FeedDetailUiState> = mockk(relaxed = true)

    private val detailInfo = flowOf(NetworkState.Success(CatDetailInfo(getMockCatInfo(), emptyList())))

    private val breedId = "abys"

    @Before
    fun setup() {
        every { detailUseCase.invoke(breedId) } returns detailInfo
        val savedStateHandle = SavedStateHandle()
        savedStateHandle[CAT_BREED_ID] = breedId
        viewModel = FeedDetailViewModel(detailUseCase, addFavoriteUseCase, savedStateHandle)
        viewModel.feedDetailUiState.observeForever(observer)
    }

    @Test
    fun `test when server response success`() = runBlocking {
        coEvery { feedApiService.getCatBreedDetail(breedId) }.returns(detailResponse)
        viewModel.getCateDetail(breedId)
        val uiState = viewModel.feedDetailUiState.value
        Truth.assertThat(uiState is FeedDetailUiState.ShowLoader).isTrue()
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
}