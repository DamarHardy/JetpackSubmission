package com.damar.jetpacksubmission.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.damar.jetpacksubmission.local.CacheMapperDetailMovieFav
import com.damar.jetpacksubmission.local.CacheMapperMovie
import com.damar.jetpacksubmission.local.CacheMapperTv
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.*
import com.damar.jetpacksubmission.utils.DataState
import com.google.android.material.tabs.TabLayout
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
    //--Get FLOW API--
    suspend fun getTrendingTv(tx : TxType): Flow<DataState<List<Tv>>> = flow {
        emit(DataState.Loading)
        try{
            when(tx){
                is TxType.UPDATE -> {
                    when(val networkMovies = remoteRepo.getTvTrending()) {
                        is NetworkResponse.APIError -> {
                            emit(DataState.Error(networkMovies.body.message))
                        }
                        is NetworkResponse.NetworkError -> {
                            emit(DataState.Error("Network Error"))
                        }
                        is NetworkResponse.UnknownError -> {
                            emit(DataState.Error("Unknown Error"))
                        }
                        is NetworkResponse.Success -> {
                            if (networkMovies.body.results != null) {
                                val networkResult =
                                    NetworkMapperTvTrending.mapFromEntityList(networkMovies.body.results)
                                val mappedResult = CacheMapperTv.mapToEntityList(networkResult)
                                localRepo.insertTv(mappedResult)
                                val cachedResults = localRepo.getTvTrending()
                                emit(DataState.Success(CacheMapperTv.mapFromEntityList(cachedResults)))
                            }
                        }
                    }
                }
                is TxType.GET ->{
                    val cachedResult = localRepo.getTvTrending()
                    emit(DataState.Success(CacheMapperTv.mapFromEntityList(cachedResult)))
                }
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getPopularTv(tx : TxType): Flow<DataState<List<Tv>>> = flow {
        emit(DataState.Loading)
        try{
            when(tx){
                is TxType.UPDATE -> {
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
                }
                is TxType.GET ->{
                    val cachedResults = localRepo.getTvPopular()
                    emit(DataState.Success(CacheMapperTv.mapFromEntityList(cachedResults)))
                }
            }

        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getTrendingMovie(tx : TxType): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        try{
            when(tx){
                is TxType.UPDATE -> {
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
                }
                is TxType.GET -> {
                    val cachedResults = localRepo.getMvTrending()
                    emit(DataState.Success(CacheMapperMovie.mapFromEntityList(cachedResults)))
                }
            }

        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getPopularMovie(tx : TxType): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        try{
            when(tx){
                is TxType.UPDATE -> {
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
                }
                is TxType.GET -> {
                    val cachedResults = localRepo.getMvPopular()
                    emit(DataState.Success(CacheMapperMovie.mapFromEntityList(cachedResults)))
                }
            }

        }catch (e: Exception){
            emit(DataState.Error(e.message!!))
        }
    }.flowOn(dispatcher)
    suspend fun getDetailMovie(id: Int): Flow<DataState<DetailMv>> = flow {
        println("Called Detail Movie with id : $id")
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

    //--Favourite API
    fun getFavouriteMovie(): PagingSource<Int, FavMoviesEntity> = localRepo.favMoviesPagingSource()
    fun isFavouriteMovie(id: String) = localRepo.getFavouriteMovies(id)
    suspend fun deleteFavourite(id: String, table: Table){
        when(table){
            Table.FavMovie ->{
                localRepo.deleteFavMovies(id)
            }
            Table.FavTv -> {
                //Do Something Later
            }
        }
    }
    suspend fun insertFavourite(item: Any, table: Table){
        when(table){
            Table.FavMovie ->{
                if(item is DetailMv){
                    localRepo.insertFavMovie(CacheMapperDetailMovieFav.mapToEntity(item))
                }
            }
            Table.FavTv -> {
                //Do Something Later
            }
        }
    }

}

sealed class TxType{
    object UPDATE: TxType()
    object GET : TxType()
}
sealed class Table{
    object FavMovie: Table()
    object FavTv: Table()
}