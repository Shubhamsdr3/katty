package com.pandey.shubham.katty.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight

/**
 * Created by shubhampandey
 */

@Entity("breed_info")
data class CatBreedInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val breedId: String?,
    val imageUrl: String?,
    val name: String?,
    val weight: Weight?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val createdAt: Long,
    var isFavourite: Boolean
)
