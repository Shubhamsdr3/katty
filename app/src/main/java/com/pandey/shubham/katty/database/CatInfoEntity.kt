package com.pandey.shubham.katty.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by shubhampandey
 */

@Entity("cat_info")
data class CatInfoEntity(
    @PrimaryKey(autoGenerate = true) // can't we work with imageId ?
    val id: Int = 0,
    val catImageId: String,
    val imageUrl: String?,
    val isFavourite: Boolean,
    val createdAt: String?
)
