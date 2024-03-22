package com.pandey.shubham.katty.feature.feed.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.feature.feed.data.dtos.CatBreedResponseItem
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class FeedDataSource @Inject constructor(private val apiService: FeedApiService) : PagingSource<Int, CatBreedItemInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedItemInfo> {
        try {
            val position = params.key ?: 0
            val feedResponse = apiService.getCatBreeds(offset = 10, pageNumber = position, "DESC")
            val feedItems = feedResponse.body() as ArrayList<CatBreedResponseItem>
            val catBreedItems = feedItems.map { it.toCatBreedItem() }
            return if (feedResponse.isSuccessful) {
                LoadResult.Page(
                    data = catBreedItems,
                    prevKey = if (position == 0) null else position.minus(1),
                    nextKey = if (feedItems.isEmpty()) null else position.plus(1)
                )
            } else {
                LoadResult.Error(IOException("Error in loading"))
            }
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, CatBreedItemInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}