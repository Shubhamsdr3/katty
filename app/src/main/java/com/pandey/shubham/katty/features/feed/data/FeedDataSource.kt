package com.pandey.shubham.katty.features.feed.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.FeedApiService
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponseItem
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by shubhampandey
 */

class FeedDataSource(
    private val apiService: FeedApiService,
    private val appDatabase: AppDatabase
) : PagingSource<Int, CatBreedItemInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreedItemInfo> {
        try {
            val position = params.key ?: 0
            val breedItemList = mutableListOf<CatBreedItemInfo>()
            withContext(Dispatchers.IO) {
                val feedResponse = apiService.getCatBreeds(offset = DEFAULT_PAGE_SIZE, pageNumber = position, "DESC")
                val favouriteIdList = appDatabase.cateInfoDao().getFavouriteBreeds().map { it.breedId }
                val feedItems = feedResponse.body() as ArrayList<CatBreedResponseItem>
                breedItemList.addAll(feedItems.map { it.toCatBreedItem(favouriteIdList.contains(it.breedId)) })
            }
            return LoadResult.Page(
                data = breedItemList,
                prevKey = if (position == 0) null else position.minus(1),
                nextKey = if (breedItemList.isEmpty()) null else position.plus(1)
            )
        } catch (ex: Exception) {
            Log.e("FeedDataSource", ex.toString())
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