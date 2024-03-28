package com.pandey.shubham.katty.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.pandey.shubham.katty.core.database.AppDatabase
import com.pandey.shubham.katty.core.database.CatBreedInfoEntity
import com.pandey.shubham.katty.core.database.CatInfoDao
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by shubhampandey
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class CatInfoDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var catInfoDao: CatInfoDao

    private var currentTimeStamp = System.currentTimeMillis()

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        catInfoDao = appDatabase.cateInfoDao()
    }

    @Test
    fun insertCateInfo(): Unit = runBlocking {
        val catInfoEntity = getMockCatInfoEntity()
        catInfoDao.addFavouriteBreed(catInfoEntity)
        val catInfos = catInfoDao.getFavouriteBreeds()
        assertThat(catInfos.contains(catInfoEntity))
    }

    @Test
    fun deleteCateInfo(): Unit = runBlocking {
        val catInfoEntity = getMockCatInfoEntity()
        catInfoDao.addFavouriteBreed(catInfoEntity)
        catInfoDao.removeFavorite(catInfoEntity.breedId)
        val catInfos = catInfoDao.getFavouriteBreeds()
        assertThat(catInfos).doesNotContain(catInfoEntity)
    }

    private fun getMockCatInfoEntity(): CatBreedInfoEntity {
        return  CatBreedInfoEntity(
            breedId = "abys",
            imageUrl = "",
            name = "Abyssinian",
            weight = Weight(imperial = "7  -  10", metric = "3 - 5"),
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            origin = "Egypt",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            createdAt = currentTimeStamp,
            lifeSpan = "14 - 15",
            isFavourite = true
        )
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}