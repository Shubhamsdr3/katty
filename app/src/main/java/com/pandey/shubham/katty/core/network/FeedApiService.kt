package com.pandey.shubham.katty.core.network

import com.pandey.shubham.katty.feature.feed.data.dtos.CatBreedResponse
import com.pandey.shubham.katty.feature.feed.data.dtos.CatImageResponse
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

    @GET("v1/images/{id}")
    suspend fun getCatImageDetail(@Path("id") catId: String): Response<CatImageResponse>
}