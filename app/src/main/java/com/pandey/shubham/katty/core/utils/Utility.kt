package com.pandey.shubham.katty.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by shubhampandey
 */
object Utility {

    fun getImageUrl(imageRefId: String?) = "https://cdn2.thecatapi.com/images/${imageRefId}.jpg"
    
    fun getAspectRatio(imageWidth: Int?, imageHeight: Int?): Float {
        val width: Int = imageWidth ?: 0
        val height: Int = imageHeight ?: 0
        if (width != 0 && height != 0) return (width.toFloat() / height)
        return 0f
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}