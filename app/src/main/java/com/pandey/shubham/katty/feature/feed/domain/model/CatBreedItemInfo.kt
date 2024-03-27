package com.pandey.shubham.katty.feature.feed.domain.model


import androidx.annotation.Keep
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight

@Keep
data class CatBreedItemInfo(
    val breedId: String,
    val imageUrl: String?,
    val name: String?,
    val weight: Weight?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val lifeSpan:String?,
    var isFavourite: Boolean = false
) {

    fun toBreedInfoEntity(): CatBreedInfoEntity {
        return CatBreedInfoEntity(
            breedId = breedId,
            imageUrl = imageUrl,
            name = name,
            weight = weight,
            temperament = temperament,
            origin = origin,
            description = description,
            lifeSpan = lifeSpan,
            isFavourite = isFavourite,
            createdAt = System.currentTimeMillis()
        )
    }
}