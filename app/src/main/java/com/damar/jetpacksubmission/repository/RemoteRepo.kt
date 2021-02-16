package com.damar.jetpacksubmission.repository

import android.util.Log
import com.damar.jetpacksubmission.models.*
import com.damar.jetpacksubmission.network.MoviedbAPI
import com.damar.jetpacksubmission.network.NetworkResponse


class RemoteRepo: IRepository {
    private fun println(s: String){
        Log.d(TAG, s)
    }
    companion object{
        private const val TAG = "RemoteRepo"
        val instance by lazy{
            RemoteRepo()
        }
    }

    override suspend fun getTvTrending(): List<TvTrending> {
        val list = mutableListOf<TvTrending>()
        when(val result = MoviedbAPI.retrofitService.getTvTrending()){
            is NetworkResponse.Success -> {
                println("TV Trending Time: ${result.body.results?.size}")
                result.body.results?.forEach { it -> list.add(it) }
            }
            is NetworkResponse.APIError -> println("ApiError ${result.body.message}")
            is NetworkResponse.NetworkError -> println("Network Error ${result.error}")
            is NetworkResponse.UnknownError -> println("UnknownError ${result.error}")
        }
        return list
    }

    override suspend fun getTvPopular(): List<TvPopular> {
        val list = mutableListOf<TvPopular>()
        when(val result = MoviedbAPI.retrofitService.getTvPopular()) {
            is NetworkResponse.Success -> {
                println("TV Popular Time: ${result.body.results?.size}")
                result.body.results?.forEach { it -> list.add(it) }
            }
            is NetworkResponse.APIError -> println("ApiError ${result.body.message}")
            is NetworkResponse.NetworkError -> println("Network Error ${result.error}")
            is NetworkResponse.UnknownError -> println("UnknownError ${result.error}")
        }
        return list
    }

    override suspend fun getMvTrending(): List<MvTrending> {
        val list = mutableListOf<MvTrending>()
        when(val result = MoviedbAPI.retrofitService.getMoviesTrending()) {
            is NetworkResponse.Success -> {
                println("Movie Trending Time: ${result.body.results?.size}")
                result.body.results?.forEach { it -> list.add(it) }
            }
            is NetworkResponse.APIError -> println("ApiError ${result.body.message}")
            is NetworkResponse.NetworkError -> println("Network Error ${result.error}")
            is NetworkResponse.UnknownError -> println("UnknownError ${result.error}")
        }
        return list
    }

    override suspend fun getMvPopular(): List<MvPopular> {
        val list = mutableListOf<MvPopular>()
        when(val result = MoviedbAPI.retrofitService.getMoviesPopular()) {
            is NetworkResponse.Success -> {
                println("Movie Popular Time: ${result.body.results?.size}")
                result.body.results?.forEach { it -> list.add(it) }
            }
            is NetworkResponse.APIError -> println("ApiError ${result.body.message}")
            is NetworkResponse.NetworkError -> println("Network Error ${result.error}")
            is NetworkResponse.UnknownError -> println("UnknownError ${result.error}")
        }
        return list
    }

    override suspend fun getDetailTV(id: Int): DetailTv? {
        when(val result = MoviedbAPI.retrofitService.getTvDetail(id)){
            is NetworkResponse.Success -> {
                return result.body
            }
            is NetworkResponse.APIError -> {
                println("ApiError ${result.body.message}")
                return null
            }
            is NetworkResponse.NetworkError -> {
                println("Network Error ${result.error}")
                return null
            }
            is NetworkResponse.UnknownError -> {
                println("UnknownError ${result.error}")
                return null
            }
        }
    }

    override suspend fun getDetailMV(id: Int): DetailMv? {
        when(val result = MoviedbAPI.retrofitService.getMvDetail(id)){
            is NetworkResponse.Success -> {
                return result.body
            }
            is NetworkResponse.APIError -> {
                println("ApiError ${result.body.message}")
                return null
            }
            is NetworkResponse.NetworkError -> {
                println("Network Error ${result.error}")
                return null
            }
            is NetworkResponse.UnknownError -> {
                println("UnknownError ${result.error}")
                return null
            }
        }
    }
}