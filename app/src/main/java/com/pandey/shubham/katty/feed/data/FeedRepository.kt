package com.pandey.shubham.katty.feed.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandey.shubham.katty.feed.paging.FeedDataSource
import com.pandey.shubham.katty.network.FeedApiService
import com.pandey.shubham.katty.network.NetworkState
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class FeedRepository @Inject constructor(private val apiService: FeedApiService) {

    fun getCateImagesPaginated() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100, // TODO why ?
        ),
        pagingSourceFactory = { FeedDataSource(apiService) },
    ).flow

    fun getCatImages(offset: Int, pageNumber: Int) = flow {
        emit(NetworkState.Loading)
        try {
            val response = apiService.getCatImages(offset, pageNumber, "DESC")
            if (response.isSuccessful) {
                emit(NetworkState.Success(response.body()))
            } else {
                emit(NetworkState.Error(IOException("Something went wrong")))
            }
        } catch (ex: Exception) {
            emit(NetworkState.Error(ex))
        }
    }

    fun getCatDetail(catId: String) = flow {
        emit(NetworkState.Loading)
        try {
            val response = apiService.getCatDetail(catId)
            if (response.isSuccessful) {
                emit(NetworkState.Success(response.body()))
            } else {
                emit(NetworkState.Error(IOException("Something went wrong")))
            }
        } catch (ex: Exception) {
            emit(NetworkState.Error(ex))
        }
    }
}