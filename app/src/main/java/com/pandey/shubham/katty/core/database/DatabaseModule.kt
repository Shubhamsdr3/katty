package com.pandey.shubham.katty.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "katty.db"
        ).addMigrations(Migration_1_2).build()

    private val Migration_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""CREATE TABLE IF NOT EXISTS `breed_info` (`song_id` INTEGER NOT NULL, `lyrics` TEXT NOT NULL, PRIMARY KEY(`song_id`))""")
        }
    }
}