package com.pandey.shubham.katty.features.feed.domain.model


import androidx.annotation.Keep
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.features.feed.data.dtos.Weight
import java.util.UUID

@Keep
data class CatBreedItemInfo(
    val breedId: String?,
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
            breedId = breedId ?: UUID.randomUUID().toString(),
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