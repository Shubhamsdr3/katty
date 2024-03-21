package com.pandey.shubham.katty.network

import com.pandey.shubham.katty.feed.data.FeedItem
import com.pandey.shubham.katty.feed.data.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by shubhampandey
 */
interface FeedApiService {

    @GET("v1/images/?limit=10&page=0&order=DESC&api_key=17d94b92-754f-46eb-99a0-65be65b5d18f")
    suspend fun getCatImages() : Response<FeedResponse>

    @GET("v1/images/{id}")
    suspend fun getCatDetail(@Path("id") catId: String): Response<FeedItem>
}