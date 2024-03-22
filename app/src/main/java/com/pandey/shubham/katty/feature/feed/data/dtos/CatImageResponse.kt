package com.pandey.shubham.katty.feature.feed.data.dtos

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class CatImageResponse(
    val id: String?,
    @SerializedName("url") val imageUrl: String?,
    val width: Int?,
    val height: Int?
): Parcelable