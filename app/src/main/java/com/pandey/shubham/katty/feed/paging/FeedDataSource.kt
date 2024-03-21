package com.pandey.shubham.katty.feed.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandey.shubham.katty.feed.data.FeedItem
import com.pandey.shubham.katty.network.FeedApiService
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class FeedDataSource @Inject constructor(private val apiService: FeedApiService) : PagingSource<Int, FeedItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedItem> {
        try {
            val position = params.key ?: 0
            val feedResponse = apiService.getCatImages(offset = 10, pageNumber = position, "DESC")
            val feedItems = feedResponse.body() as ArrayList<FeedItem> // TODO: fixme
            return if (feedResponse.isSuccessful) {
                LoadResult.Page(
                    data = feedItems,
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
    override fun getRefreshKey(state: PagingState<Int, FeedItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}