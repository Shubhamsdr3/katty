package com.pandey.shubham.katty.features.feed.data.dtos


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.pandey.shubham.katty.core.utils.Utility
import com.pandey.shubham.katty.features.feed.domain.model.CatBreedItemInfo
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CatBreedResponseItem(
    @SerializedName("id") val breedId: String,
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
    val origin: String?,
    @SerializedName("life_span") val lifeSpan: String?
): Parcelable  {

    fun toCatBreedItem(isFavourite: Boolean = false): CatBreedItemInfo {
        return CatBreedItemInfo(
            breedId = breedId,
            imageUrl = Utility.getImageUrl(imageId), // TODO: get this from api.
            name = name,
            weight = weight,
            temperament = temperament,
            origin = origin,
            description = description,
            lifeSpan = lifeSpan,
            isFavourite = isFavourite
        )
    }
}