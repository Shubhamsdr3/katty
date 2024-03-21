package com.pandey.shubham.katty.network

/**
 * Created by shubhampandey
 */
sealed class NetworkState<out T: Any> {

    data class Success<out T: Any>(val data: T? = null): NetworkState<T>()

    data class Error(val throwable: Throwable): NetworkState<Nothing>()

    data object Loading: NetworkState<Nothing>()
}