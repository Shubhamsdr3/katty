package com.pandey.shubham.katty.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Created by shubhampandey
 */

@Dao
interface CatInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteBreed(catBreed: CatBreedInfoEntity)

    @Query("DELETE FROM breed_info WHERE breedId=:catBreedId")
    suspend fun removeFavorite(catBreedId: String)

    @Query("SELECT * FROM breed_info ORDER BY createdAt DESC")
    suspend fun getFavouriteBreeds(): List<CatBreedInfoEntity>

    @Query("SELECT * FROM breed_info WHERE breedId=:breedId")
    fun getFavouriteBreed(breedId: String?): Flow<CatBreedInfoEntity?>

    @Upsert
    suspend fun addUpdateBreeds(items: List<CatBreedInfoEntity>)

    @Query("SELECT * FROM breed_info")
    fun pagingSource(): PagingSource<Int, CatBreedInfoEntity>

    @Query("DELETE FROM breed_info")
    suspend fun deleteAll()
}