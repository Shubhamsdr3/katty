package com.pandey.shubham.katty.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

/**
 * Created by shubhampandey
 */

@Dao
interface CatInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteBreed(catList: CatBreedInfoEntity)

    @Query("SELECT * FROM breed_info ORDER BY createdAt DESC")
    suspend fun getFavouriteBreeds(): List<CatBreedInfoEntity>

    @Query("DELETE FROM breed_info")
    suspend fun deleteAll()
}