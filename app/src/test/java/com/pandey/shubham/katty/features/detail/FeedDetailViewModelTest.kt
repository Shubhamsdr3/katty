package com.pandey.shubham.katty.features.detail

import com.pandey.shubham.katty.features.detail.ui.FeedDetailViewModel
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepositoryImpl
import com.pandey.shubham.katty.features.feed.domain.usecase.AddFavoriteUseCase
import com.pandey.shubham.katty.features.detail.domain.usecase.GetCatDetailUseCase
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

/**
 * Created by shubhampandey
 */
class FeedDetailViewModelTest {

    private lateinit var detailViewModel: FeedDetailViewModel

    private val feedRepository: FeedRepositoryImpl = mockk()

    private lateinit var getCatDetailUseCase: GetCatDetailUseCase
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Before
    fun setUp() {
        getCatDetailUseCase = GetCatDetailUseCase(feedRepository)
        addFavoriteUseCase = AddFavoriteUseCase(feedRepository)
        detailViewModel = FeedDetailViewModel(getCatDetailUseCase, addFavoriteUseCase)
    }

    @Test
    fun `test if data is fetched from cached`() {

    }
}