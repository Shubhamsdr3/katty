package com.pandey.shubham.katty.feed.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FeedItem(
    val id: String?,
    @SerializedName("url") val imageUrl: String?,
    val createdAt: String?,
    val height: Int?,
    val width: Int?
)