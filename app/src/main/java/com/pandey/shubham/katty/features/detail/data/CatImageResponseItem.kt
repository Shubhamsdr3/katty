package com.pandey.shubham.katty.features.detail.data


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class CatImageResponseItem(
    val height: Int?,
    val id: String?,
    val url: String?,
    val width: Int?
): Parcelable