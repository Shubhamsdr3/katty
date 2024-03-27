package com.pandey.shubham.katty.feature.feed.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pandey.shubham.katty.core.FeedApiService
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.network.getNetworkResult
import com.pandey.shubham.katty.core.network.makeRequest
import com.pandey.shubham.katty.core.utils.DEFAULT_PAGE_SIZE
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.feature.feed.data.dtos.CatBreedResponseItem
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import kotlinx.coroutines.Dispatchers.IO

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
            val favouriteBreedList = appDatabase.cateInfoDao().getFavouriteBreeds()
            var hasNext = false
            var breedItemList: List<CatBreedItemInfo> = emptyList()
            if (Utility.isNetworkAvailable()) {
                val favouriteIdList = favouriteBreedList.map { it.breedId }
                val networkState = makeRequest(IO) {
                    apiService.getCatBreeds(offset = DEFAULT_PAGE_SIZE, pageNumber = position, "DESC")
                }
                networkState.getNetworkResult(
                    onSuccess = { response ->
                        val feedItems = response as? ArrayList<CatBreedResponseItem>
                        hasNext = !feedItems.isNullOrEmpty()
                        breedItemList = feedItems?.map { it.toCatBreedItem(favouriteIdList.contains(it.breedId)) } ?: emptyList()
                    } , onError = {
                        Log.e(TAG, it.toString())
                        breedItemList = favouriteBreedList.map { it.toCatBreedItem() }
                    }
                )
            } else {
                breedItemList = favouriteBreedList.map { it.toCatBreedItem() }
            }
            return LoadResult.Page(
                data = breedItemList,
                prevKey = if (position == 0) null else position.minus(1),
                nextKey = if (!hasNext) null else position.plus(1)
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