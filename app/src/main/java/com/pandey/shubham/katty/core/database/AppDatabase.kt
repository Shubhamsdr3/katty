package com.pandey.shubham.katty.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by shubhampandey
 */

@Database(entities = [CatBreedInfoEntity::class], version = 1)
@TypeConverters(WeightTypeConvertor::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun cateInfoDao(): CatInfoDao
}