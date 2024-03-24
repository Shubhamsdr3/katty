package com.pandey.shubham.katty.features.feed.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponseItem
import com.pandey.shubham.katty.features.feed.data.dtos.DataHolder
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by shubhampandey
 */

private const val TAG = "FeedDataSource"

class FeedDataSource(
    private val apiService: FeedApiService,
    private val appDatabase: AppDatabase
) : PagingSource<Int, CatBreedItemInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedItemInfo> {
        try {
            val position = params.key ?: 0
            DataHolder.currentPage = position
            val favouriteBreedList = appDatabase.cateInfoDao().getFavouriteBreeds()
            var hasNext = false
            val breedItemList: List<CatBreedItemInfo> = if (Utility.isNetworkAvailable()) {
                val favouriteIdList = favouriteBreedList.map { it.breedId }
                val feedResponse = apiService.getCatBreeds(offset = DEFAULT_PAGE_SIZE, pageNumber = position, "DESC")
                val feedItems = feedResponse.body() as ArrayList<CatBreedResponseItem>
                hasNext = feedItems.isNotEmpty()
                feedItems.map { it.toCatBreedItem(favouriteIdList.contains(it.breedId)) }
            } else {
                favouriteBreedList.map { it.toCatBreedItem() }
            }
            return LoadResult.Page(
                data = breedItemList,
                prevKey = if (position == 0) null else position.minus(1),
                nextKey = if (hasNext) null else position.plus(1)
            )
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
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