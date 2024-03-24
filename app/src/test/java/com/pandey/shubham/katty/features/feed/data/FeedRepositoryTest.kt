package com.pandey.shubham.katty.features.feed.data

import androidx.paging.Pager
import androidx.paging.PagingData
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.features.feed.data.repository.FeedRepository
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 * 1. Test weather pagination is working.
 * 2. Verify network loading/error/success state.
 * 3. Verify caching to db.
 * 4.
 */
class FeedRepositoryTest {

    private lateinit var feedRepository: FeedRepository

    private val feedApiService: FeedApiService = mockk()
    private val appDatabase: AppDatabase = mockk()
    private val utility: Utility = mockk()

    @Before
    fun setUp() {
        feedRepository = FeedRepository(feedApiService, appDatabase)
    }

    @Test
    fun `movies service should return network failure when no connection`() {
        every { utility.isNetworkAvailable() } returns false
        val breeds = feedRepository.getCateImagesPaginated()
        breeds should beInstanceOf<PagingData<CatBreedItemInfo>>()

        verify { feedApiService wasNot Called }
    }

}