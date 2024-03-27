package com.pandey.shubham.katty.core.network

/**
 * Created by shubhampandey
 */
sealed class NetworkState<out T> {

    data class Success<out T>(val data: T? = null): NetworkState<T>()

    data class Error(val throwable: Throwable): NetworkState<Nothing>()
}