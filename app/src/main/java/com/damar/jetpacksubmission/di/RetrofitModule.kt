package com.damar.jetpacksubmission.di

import com.damar.jetpacksubmission.network.IMoviedbAPI
import com.damar.jetpacksubmission.network.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    @Singleton
    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
    }
    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit.Builder): IMoviedbAPI{
        return retrofit.build()
                .create(IMoviedbAPI::class.java)
    }
}