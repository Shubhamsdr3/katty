package com.pandey.shubham.katty.feature.feed.data.dtos


import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.UiThread
import com.google.gson.annotations.SerializedName
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CatBreedResponseItem(
    val id: String?,
    val weight: Weight?,
    val indoor: Int?,
    val name: String?,
    @SerializedName("reference_image_id")
    val imageId: String?,
    @SerializedName("alt_names") val alternateNames: String?,
    val adaptability: Int?,
    @SerializedName("affection_level") val affectionLevel: Int?,
    val temperament: String?,
    val description: String?,
    val origin: String?
): Parcelable  {

    fun toCatBreedItem(): CatBreedItemInfo {
        return CatBreedItemInfo(
            breedId = id,
            imageUrl = Utility.getImageUrl(imageId),
            name = name,
            weight = weight,
            temperament = temperament,
            origin = origin,
            description = description,
        )
    }
}