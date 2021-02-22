package com.damar.jetpacksubmission.ui.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.damar.jetpacksubmission.local.CacheMapperMovieFav
import com.damar.jetpacksubmission.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    repository: Repository
): ViewModel() {

    val movies = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200
        )
    ){
        repository.getFavouriteMovie()
    }.flow.cachedIn(viewModelScope)
        .map {pagingData ->
            pagingData.map {
                CacheMapperMovieFav.mapFromEntity(it)
            }
        }
}