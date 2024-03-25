package com.pandey.shubham.katty.features.detail.domain.model

import androidx.annotation.Keep
import com.pandey.shubham.katty.features.detail.data.CatImageResponseItem
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo

/**
 * Created by shubhampandey
 */

@Keep
data class CatDetailInfo(
    val breedItemInfo: CatBreedItemInfo?,
    val images: List<CatImageResponseItem>?
)