package com.pandey.shubham.katty.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Created by shubhampandey
 */

private const val NETWORK_TIMEOUT = 1000L
const val NETWORK_ERROR_UNKNOWN = "Something went wrong"
suspend fun <T : Any> makeRequest(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): NetworkState<T> {
    return withContext(dispatcher) {
        try {
            val response = withTimeout(NETWORK_TIMEOUT) { apiCall.invoke() } // throws TimeoutCancellationException
            if (response.isSuccessful) {
                NetworkState.Success(response.body())
            } else {
                NetworkState.Error(throwable = IOException(NETWORK_ERROR_UNKNOWN))
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            handleError(throwable)
        }
    }
}

private fun handleError(throwable: Throwable) = when (throwable) {
    is TimeoutCancellationException -> {
        NetworkState.Error(throwable)
    }

    is IOException -> {
        NetworkState.Error(throwable = throwable)
    }

    is HttpException -> {
        NetworkState.Error(throwable)
    }

    else -> {
        NetworkState.Error(throwable = Exception(NETWORK_ERROR_UNKNOWN))
    }
}

suspend fun <T> NetworkState<T>.getNetworkResult(onSuccess: suspend (T?) -> Unit, onError: suspend (Throwable) -> Unit) {
    when(this) {
        is NetworkState.Success -> onSuccess(data)
        is NetworkState.Error -> onError(throwable)
    }
}