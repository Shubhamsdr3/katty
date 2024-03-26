package com.pandey.shubham.katty.features.detail

import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.NetworkState
import com.pandey.shubham.katty.features.detail.ui.FeedDetailViewModel
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatDetailUseCase
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import io.mockk.Called
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Created by shubhampandey
 */
class FeedDetailViewModelTest {

    private lateinit var detailViewModel: FeedDetailViewModel

    private val feedRepository: FeedRepositoryImpl = mockk()

    private val appDatabase: AppDatabase = mockk()

    private lateinit var getCatDetailUseCase: GetCatDetailUseCase
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    private val catBreedItemInfo: CatBreedItemInfo = mockk()

    private val breedId = "aege"

    @Before
    fun setUp() {
        getCatDetailUseCase = GetCatDetailUseCase(feedRepository)
        addFavoriteUseCase = AddFavoriteUseCase(feedRepository)
        detailViewModel = FeedDetailViewModel(getCatDetailUseCase, addFavoriteUseCase)
    }

    @Test
    fun `test if data is fetched from cache`() {
        runBlocking {
            every { getCatDetailUseCase(breedId) } returns flowOf(NetworkState.Success(catBreedItemInfo))
            getCatDetailUseCase(breedId)
            coVerify { feedRepository.getCatBreedDetail(breedId) wasNot Called }
        }
    }
}