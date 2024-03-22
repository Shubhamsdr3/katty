package com.pandey.shubham.katty.feature.feed.data.dtos

import android.os.Parcelable
import androidx.annotation.Keep
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
class CatBreedResponse : ArrayList<CatBreedResponseItem>(), Parcelable