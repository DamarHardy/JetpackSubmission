package com.damar.jetpacksubmission.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository, private val dispatcher: CoroutineDispatcher): ViewModel() {
    private var _detail = MutableLiveData<State<Any>>()
    val detail: LiveData<State<Any>> get()= _detail

    fun getDetailMv(id: Int){
        EspressoIdlingResource.increment()
        viewModelScope.launch(dispatcher) {
            _detail.postValue(State.Loading())
            val result = repository.getDetailMv(id)
            if(result!=null){
                _detail.postValue(State.Success(result))
            }else{
                _detail.postValue(State.Failed())
            }
        }
    }
    fun getDetailTv(id: Int){
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _detail.postValue(State.Loading())
            val result = repository.getDetailTv(id)
            if(result!=null){
                _detail.postValue(State.Success(result))
            }else{
                _detail.postValue(State.Failed())
            }
        }
    }
}

sealed class State<out T: Any>{
    data class Success<T: Any>(val body: T, val message: String = "SUCCESS"): State<T>()
    data class Failed(val message: String = "FAILED"): State<Nothing>()
    data class Loading(val message: String = "LOADING"): State<Nothing>()
}