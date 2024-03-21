package com.pandey.shubham.katty.feed.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.pandey.shubham.katty.database.CatInfoEntity
import java.util.UUID

@Keep
data class FeedItem(
    val id: String?,
    @SerializedName("url") val imageUrl: String?,
    val createdAt: String?,
    val height: Int?,
    val width: Int?,
    var isFavourite: Boolean = false
)

fun FeedItem.toCatInfo(): CatInfoEntity {
//    if (this.imageUrl.isNullOrBlank()) return null // won't save the data which don't have image.
    return CatInfoEntity(
        catImageId = id ?: UUID.randomUUID().toString(),
        imageUrl = imageUrl,
        isFavourite = false,
        createdAt = createdAt
    )
}