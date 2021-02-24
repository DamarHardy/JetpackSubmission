package com.damar.jetpacksubmission.ui.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.damar.jetpacksubmission.local.CacheMapperMovieFav
import com.damar.jetpacksubmission.local.CacheMapperTvFav
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.repository.Table
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    val tvs = Pager(
            PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true,
                    maxSize = 200
            )
    ){
        EspressoIdlingResource.increment()
        repository.getFavouritePagingTv()
    }.flow.cachedIn(viewModelScope).map { pagingData ->
        pagingData.map {
            CacheMapperTvFav.mapFromEntity(it)
        }
    }

    val movies = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200
        )
    ){
        EspressoIdlingResource.increment()
        repository.getFavouritePaging()
    }.flow.cachedIn(viewModelScope).map {pagingData ->
            pagingData.map {
                CacheMapperMovieFav.mapFromEntity(it)
            }
        }

    fun deleteFavourite(id: Int, table: Table){
        viewModelScope.launch(dispatcher) {
            repository.deleteFavourite(id, table)
        }
    }
}