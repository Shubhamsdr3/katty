package com.pandey.shubham.katty.utils

/**
 * Created by shubhampandey
 */
object Utility {
    
    fun getAspectRatio(imageWidth: Int?, imageHeight: Int?): Float {
        val width: Int = imageWidth ?: 0
        val height: Int = imageHeight ?: 0
        if (width != 0 && height != 0) return (width.toFloat() / height)
        return 0f
    }
}