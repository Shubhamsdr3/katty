package com.pandey.shubham.katty.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by shubhampandey
 */

@Database(entities = [CatInfoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun cateInfoDao(): CatInfoDao
}