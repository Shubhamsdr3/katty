package com.pandey.shubham.katty.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight
import com.pandey.shubham.katty.feature.feed.domain.model.CatBreedItemInfo

/**
 * Created by shubhampandey
 */

@Entity("breed_info")
data class CatBreedInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val breedId: String,
    val imageUrl: String?,
    val name: String?,
    val weight: Weight?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val createdAt: Long,
    val lifeSpan: String?,
    var isFavourite: Boolean
) {

    fun toCatBreedItem(): CatBreedItemInfo {
        return CatBreedItemInfo(
            breedId, imageUrl, name, weight, temperament, origin, description, lifeSpan, isFavourite
        )
    }
}
