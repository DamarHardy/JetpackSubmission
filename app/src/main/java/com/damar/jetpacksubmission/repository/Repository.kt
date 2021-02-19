package com.damar.jetpacksubmission.repository

import android.util.Log
import com.damar.jetpacksubmission.local.CacheMapperMovie
import com.damar.jetpacksubmission.local.CacheMapperTv
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.*
import com.damar.jetpacksubmission.network.entity.TvsTrendingNetworkEntity
import com.damar.jetpacksubmission.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class Repository constructor(
        private val localRepo: LocalDao,
        private val remoteRepo: IMoviedbAPI,
        private val dispatcher: CoroutineDispatcher,
        private val movieMapper: NetworkMapperMvDetail,
        private val tvMapper: NetworkMapperTvDetail
){
    init {
        println("Repository is Being Called")
    }
    //--Get FLOW API--
    suspend fun getTrendingTv(): Flow<DataState<List<Tv>>> = flow {
        println("Get Trending TV called")
        emit(DataState.Loading)
        try{
            when(val networkMovies = remoteRepo.getTvTrending()){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkMovies.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    if(networkMovies.body.results != null){
                        val networkResult = NetworkMapperTvTrending.mapFromEntityList(networkMovies.body.results)
                        val mappedResult = CacheMapperTv.mapToEntityList(networkResult)
                        localRepo.insertTv(mappedResult)
                        val cachedResults = localRepo.getTvTrending()
                        emit(DataState.Success(CacheMapperTv.mapFromEntityList(cachedResults)))
                    }

                }
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getPopularTv(): Flow<DataState<List<Tv>>> = flow {
        emit(DataState.Loading)
        try{
            when(val networkMovies = remoteRepo.getTvPopular()){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkMovies.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    if(networkMovies.body.results != null){
                        val networkResult = NetworkMapperTvPopular.mapFromEntityList(networkMovies.body.results)
                        val mappedResult = CacheMapperTv.mapToEntityList(networkResult)
                        localRepo.insertTv(mappedResult)
                        val cachedResults = localRepo.getTvPopular()
                        emit(DataState.Success(CacheMapperTv.mapFromEntityList(cachedResults)))
                    }

                }
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getTrendingMovie(): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        try{
            when(val networkMovies = remoteRepo.getMoviesTrending()){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkMovies.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    if(networkMovies.body.results != null){
                        val networkResult = NetworkMapperMvTrending.mapFromEntityList(networkMovies.body.results)
                        val mappedResult = CacheMapperMovie.mapToEntityList(networkResult)
                        localRepo.insertMovie(mappedResult)
                        val cachedResults = localRepo.getMvTrending()
                        emit(DataState.Success(CacheMapperMovie.mapFromEntityList(cachedResults)))
                    }
                }
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getPopularMovie(): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        try{
            when(val networkMovies = remoteRepo.getMoviesPopular()){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkMovies.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    if(networkMovies.body.results != null){
                        val networkResult = NetworkMapperMvPopular.mapFromEntityList(networkMovies.body.results)
                        val mappedResult = CacheMapperMovie.mapToEntityList(networkResult)
                        localRepo.insertMovie(mappedResult)
                        val cachedResults = localRepo.getMvPopular()
                        emit(DataState.Success(CacheMapperMovie.mapFromEntityList(cachedResults)))
                    }
                }
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getDetailMovie(id: Int): Flow<DataState<DetailMv>> = flow {
        emit(DataState.Loading)
        try{
            when(val networkResponse = remoteRepo.getMvDetail(id)){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkResponse.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    val mappedResult = movieMapper.mapFromEntity(networkResponse.body)
                    emit(DataState.Success(mappedResult))
                }
            }
        }catch (e: Exception){
            emit(DataState.Error("${e.message}"))
        }
    }.flowOn(dispatcher)
    suspend fun getDetailTv(id: Int): Flow<DataState<DetailTv>> = flow {
        emit(DataState.Loading)
        try{
            when(val networkResp = remoteRepo.getTvDetail(id)){
                is NetworkResponse.APIError ->{emit(DataState.Error(networkResp.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("Network Error"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("Unknown Error"))}
                is NetworkResponse.Success -> {
                    val mappedResp = tvMapper.mapFromEntity(networkResp.body)
                    emit(DataState.Success(mappedResp))
                }
            }
        }catch (e: Exception){
            emit(DataState.Error("${e.message}"))
        }
    }.flowOn(dispatcher)

    private fun println(s: String){
        Log.d(TAG, s)
    }
    companion object {
        private const val TAG = "Repository"
    }
}