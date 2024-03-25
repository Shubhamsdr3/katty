package com.pandey.shubham.katty.core.network

import com.pandey.shubham.katty.features.detail.data.CatImageResponse
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponse
import com.pandey.shubham.katty.features.feed.data.dtos.CatBreedResponseItem
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
    ) : Response<CatBreedResponse>

    @GET("v1/breeds/{id}")
    suspend fun getCatBreedDetail(@Path("id") catBreedId: String): Response<CatBreedResponseItem>

    @GET("v1/images/search")
    suspend fun getCatImages(@Query("breed_ids") catBreedId: String): Response<CatImageResponse>
}