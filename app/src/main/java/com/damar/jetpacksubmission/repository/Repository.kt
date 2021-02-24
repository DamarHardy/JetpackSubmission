package com.damar.jetpacksubmission.repository

import androidx.paging.PagingSource
import com.damar.jetpacksubmission.local.*
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
import com.damar.jetpacksubmission.local.entity.FavTvsEntity
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.*
import com.damar.jetpacksubmission.network.entity.SearchResultNetworkEntity
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
        private val tvMapper: NetworkMapperTvDetail,
        private val cacheMovieMapper: CacheMapperDetailMovieFav,
        private val cacheTvMapper: CacheMapperDetailTvFav
){
    //--Home and Detail API--
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
    fun getFavouritePaging(): PagingSource<Int, FavMoviesEntity> = localRepo.favMoviesPagingSource()
    fun getFavouritePagingTv(): PagingSource<Int, FavTvsEntity> = localRepo.favTvsPagingSource()
    suspend fun isFavourite(id: Int, table: Table): Boolean{
        return when(table){
            Table.FavMovie -> {
                val lists = localRepo.getFavouriteMovies(id)
                lists.isNotEmpty()
            }
            Table.FavTv -> {
                val lists = localRepo.getFavouriteTvs(id)
                lists.isNotEmpty()
            }
        }

    }
    suspend fun deleteFavourite(id: Int, table: Table){
        when(table){
            Table.FavMovie ->{
                localRepo.deleteFavMovies(id)
            }
            Table.FavTv -> {
                localRepo.deleteFavTv(id)
            }
        }
    }
    suspend fun insertFavourite(item: Any, table: Table){
        when(table){
            Table.FavMovie ->{
                if(item is DetailMv){
                    localRepo.insertFavMovie(cacheMovieMapper.mapToEntity(item))
                }
            }
            Table.FavTv -> {
                if(item is DetailTv){
                    localRepo.insertFavTv(cacheTvMapper.mapToEntity(item))
                }
            }
        }
    }

    //--Search API
    suspend fun getSearchResult(query: String): Flow<DataState<SearchResultNetworkEntity>> = flow{
        emit(DataState.Loading)
        try{
            when(val result = remoteRepo.searchItem(query)){
                is NetworkResponse.APIError ->{emit(DataState.Error(result.body.message))}
                is NetworkResponse.NetworkError -> {emit(DataState.Error("${result.error.message}"))}
                is NetworkResponse.UnknownError -> {emit(DataState.Error("${result.error.message}"))}
                is NetworkResponse.Success -> {
                    emit(DataState.Success(result.body))
                }
            }
        }catch (e: Exception){
            emit(DataState.Error("${e.message}"))
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