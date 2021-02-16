package com.damar.jetpacksubmission.network

import com.damar.jetpacksubmission.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYTJhYWRhMGQ1NjM4NmJkOGZlNTZmMzcxZjU0OTNkZSIsInN1YiI6IjYwMDZiNTBmODY5ZTc1MDAzZDg1NmNkZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VkBLSpIhrYaB0cIScgM0SpWKPperQCQM_MIjIfQN8YI"
private const val BASE_URL = "https://api.themoviedb.org/3/"
const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(NetworkResponseAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(OkHttpClient.Builder().build())
    .build()

interface IMoviedbAPI {
    @GET("trending/movie/week")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMoviesTrending(): NetworkResponse<MoviesTrendingMoshi, Error>

    @GET("movie/popular")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMoviesPopular(): NetworkResponse<MoviesPopularMoshi, Error>

    @GET("trending/tv/week")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvTrending(): NetworkResponse<TvTrendingMoshi, Error>

    @GET("tv/popular")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvPopular(): NetworkResponse<TvPopularMoshi, Error>

    @GET("movie/{movie_id}?append_to_response=images")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMvDetail(@Path("movie_id") id: Int): NetworkResponse<DetailMv, Error>

    @GET("tv/{tv_id}?append_to_response=images")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvDetail(@Path("tv_id") id: Int): NetworkResponse<DetailTv, Error>

}

object MoviedbAPI{
    val retrofitService: IMoviedbAPI by lazy{
        retrofit.create(IMoviedbAPI::class.java)
    }
}