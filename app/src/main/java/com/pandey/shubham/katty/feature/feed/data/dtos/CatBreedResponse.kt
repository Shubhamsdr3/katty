package com.pandey.shubham.katty.feature.feed.data.dtos

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
class CatBreedResponse : ArrayList<CatBreedResponseItem>(), Parcelable