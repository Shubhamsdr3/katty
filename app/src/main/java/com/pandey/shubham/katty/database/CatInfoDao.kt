package com.pandey.shubham.katty.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Created by shubhampandey
 */

@Dao
interface CatInfoDao {

    @Upsert
    fun addCatInfo(catList: List<CatInfoEntity>)

    @Query("SELECT * FROM cat_info ORDER BY createdAt DESC")
    fun pagingSource(): PagingSource<Int, CatInfoEntity>

    @Query("DELETE FROM cat_info")
    fun deleteAll()
}