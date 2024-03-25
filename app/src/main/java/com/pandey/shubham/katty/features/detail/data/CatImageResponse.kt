package com.pandey.shubham.katty.features.detail.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
class CatImageResponse : ArrayList<CatImageResponseItem>(), Parcelable