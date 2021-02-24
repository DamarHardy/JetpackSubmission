package com.damar.jetpacksubmission.network

import com.damar.jetpacksubmission.network.entity.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYTJhYWRhMGQ1NjM4NmJkOGZlNTZmMzcxZjU0OTNkZSIsInN1YiI6IjYwMDZiNTBmODY5ZTc1MDAzZDg1NmNkZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VkBLSpIhrYaB0cIScgM0SpWKPperQCQM_MIjIfQN8YI"
const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
interface IMoviedbAPI {
    @GET("trending/movie/week")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMoviesTrending(): NetworkResponse<MoviesTrendingNetworkEntity, Error>

    @GET("movie/popular")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMoviesPopular(): NetworkResponse<MoviesPopularNetworkEntity, Error>

    @GET("trending/tv/week")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvTrending(): NetworkResponse<TvsTrendingNetworkEntity, Error>

    @GET("tv/popular")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvPopular(): NetworkResponse<TvsPopularNetworkEntity, Error>

    @GET("movie/{movie_id}?append_to_response=images")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getMvDetail(@Path("movie_id") id: Int): NetworkResponse<DetailMvNetworkEntity, Error>

    @GET("tv/{tv_id}?append_to_response=images")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun getTvDetail(@Path("tv_id") id: Int): NetworkResponse<DetailTvNetworkEntity, Error>

    @GET("search/multi")
    @Headers("Authorization: Bearer $TOKEN")
    suspend fun searchItem(@Query("query") query: String): NetworkResponse<SearchResultNetworkEntity, Error>
}
