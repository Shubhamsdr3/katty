package com.pandey.shubham.katty.features.feed.data.dtos

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
class CatBreedResponse : ArrayList<CatBreedResponseItem>(), Parcelable