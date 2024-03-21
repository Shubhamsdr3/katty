package com.pandey.shubham.katty.feed.data

import com.pandey.shubham.katty.network.FeedApiService
import com.pandey.shubham.katty.network.NetworkState
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Created by shubhampandey
 */

class FeedRepository @Inject constructor(private val apiService: FeedApiService) {

    fun getCatImages() = flow {
        emit(NetworkState.Loading)
        try {
            val response = apiService.getCatImages()
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