package com.pandey.shubham.katty.feature.feed.data
//
//import android.net.http.HttpException
//import android.os.Build
//import androidx.annotation.RequiresExtension
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.pandey.shubham.katty.database.AppDatabase
//import com.pandey.shubham.katty.database.CatInfoEntity
//import com.pandey.shubham.katty.network.FeedApiService
//
///**
// * Created by shubhampandey
// */
//@OptIn(ExperimentalPagingApi::class)
//class FeedRemoteMediator(
//    private val appDatabase: AppDatabase,
//    private val apiService: FeedApiService
//): RemoteMediator<Int, CatInfoEntity>() {
//
//    override suspend fun load(loadType: LoadType, state: PagingState<Int, CatInfoEntity>): MediatorResult {
//        return try {
//            val loadKey = when(loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) 1
//                    else (lastItem.id / state.config.pageSize) + 1
//                }
//            }
//            val response = apiService.getCatBreeds(offset = state.config.pageSize, loadKey, "DESC")
//            appDatabase.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    appDatabase.cateInfoDao().deleteAll()
//                }
////                val catEntities: List<CatInfoEntity>? = response.body()?.map { it.toCatInfo() }
////                if (!catEntities.isNullOrEmpty()) {
////                    appDatabase.cateInfoDao().addCatInfo(catEntities)
////                }
//            }
////            MediatorResult.Success(
////                endOfPaginationReached = response.body().isNullOrEmpty()
////            )
//        } catch (ex: Exception) {
//            MediatorResult.Error(ex) // TODO handle specific exception.
//        }
//    }
//}