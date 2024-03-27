package com.pandey.shubham.katty.core.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pandey.shubham.katty.BuildConfig
import com.pandey.shubham.katty.core.FeedApiService
import com.pandey.shubham.katty.core.utils.getAppContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by shubhampandey
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FeedApiService = retrofit.create(FeedApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okhttp: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttp)
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .enableComplexMapKeySerialization()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        getAppContext { context ->
            if (BuildConfig.DEBUG) {
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
                builder.addInterceptor(logging)
                builder.addInterceptor(ChuckerInterceptor.Builder(context).build())
                builder.addInterceptor(headerInterceptor)
            } else {
                logging.apply { logging.level = HttpLoggingInterceptor.Level.NONE }
            }
        }
        return builder.build()
    }

    private val headerInterceptor: Interceptor
        get() = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("x-api-key", BuildConfig.CAT_API_KEY)
            chain.proceed(requestBuilder.build())
        }
}