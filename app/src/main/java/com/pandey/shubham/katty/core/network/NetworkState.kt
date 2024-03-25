package com.pandey.shubham.katty.core.network

/**
 * Created by shubhampandey
 */
sealed class NetworkState<out T: Any> {

    data class Success<out T: Any>(val data: T? = null): NetworkState<T>()

    data class Error(val throwable: Throwable): NetworkState<Nothing>()

    data object Loading: NetworkState<Nothing>()
}

fun <T: Any> NetworkState<T>.getResult(): T? = when (this) {
    is NetworkState.Success -> data
    else -> null
}