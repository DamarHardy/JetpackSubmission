package com.damar.jetpacksubmission.ui.detail.viewmodel

import androidx.lifecycle.*
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.repository.Table
import com.damar.jetpacksubmission.utils.DataState
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
        private val repository: Repository,
        private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private var _detail = MutableLiveData<DataState<Any>>()
    val detail: LiveData<DataState<Any>> get()= _detail

    suspend fun isFavourite(id: Int, table: Table): Boolean {
        return repository.isFavourite(id, table)
    }
    fun insertFavourite(item: Any, table: Table){
        viewModelScope.launch(dispatcher) {
            repository.insertFavourite(item, table)
        }
    }
    fun deleteFavourite(id: Int, table: Table){
        viewModelScope.launch(dispatcher) {
            repository.deleteFavourite(id, table)
        }
    }
    fun getMovieDetail(id: Int){
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            repository.getDetailMovie(id).onEach {
                when(it){
                    is DataState.Error -> _detail.value = it
                    is DataState.Loading -> _detail.value = it
                    is DataState.Success -> _detail.value = it
                }
            }.launchIn(viewModelScope)
        }
    }
    fun getTvDetail(id: Int){
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            repository.getDetailTv(id).onEach {
                when(it){
                    is DataState.Error -> _detail.value = it
                    is DataState.Loading -> _detail.value = it
                    is DataState.Success -> _detail.value = it
                }
            }.launchIn(viewModelScope)
        }
    }
}