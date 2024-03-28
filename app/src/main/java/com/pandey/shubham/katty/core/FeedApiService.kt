package com.pandey.shubham.katty.core

import com.pandey.shubham.katty.feature.detail.data.CatImageResponse
import com.pandey.shubham.katty.feature.feed.data.dtos.CatDetailResponse
import com.pandey.shubham.katty.feature.feed.data.dtos.CatDetailItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by shubhampandey
 */
interface FeedApiService {

    @GET("v1/breeds")
    suspend fun getCatBreeds(
        @Query("limit") offset: Int,
        @Query("page") pageNumber: Int,
        @Query("order") orderBy: String
    ) : Response<CatDetailResponse>

    @GET("v1/breeds/{id}")
    suspend fun getCatBreedDetail(@Path("id") catBreedId: String): Response<CatDetailItemResponse>

    @GET("v1/images/search")
    suspend fun getCatImages(@Query("breed_ids") catBreedId: String): Response<CatImageResponse>
}