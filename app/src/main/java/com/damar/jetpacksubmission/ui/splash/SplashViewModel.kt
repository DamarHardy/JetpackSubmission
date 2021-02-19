package com.damar.jetpacksubmission.ui.splash

import android.util.Log
import androidx.lifecycle.*
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(
        private val repository: Repository,
        private val savedStateHandle: SavedStateHandle): ViewModel() {
    private var _state = MutableLiveData<DataState<Any>>()
    val state: LiveData<DataState<Any>> get() = _state
    suspend fun init(){
        _state.value = DataState.Loading
        withContext(viewModelScope.coroutineContext) {
            repository.getPopularTv().onEach {
                when(it){
                    is DataState.Success -> {
                        println("Popular TV: ${it.body}")
                    }
                    is DataState.Error -> {

                    }
                    is DataState.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
            repository.getTrendingTv().onEach {
                when(it){
                    is DataState.Success -> {
                        println("Trending TV: ${it.body}")
                    }
                    is DataState.Error -> {

                    }
                    is DataState.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
            repository.getPopularMovie().onEach {
                when(it){
                    is DataState.Success -> {
                        println("Popular Movie: ${it.body}")
                    }
                    is DataState.Error -> {

                    }
                    is DataState.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
            repository.getTrendingMovie().onEach {
                when(it){
                    is DataState.Success -> {
                        println("Trending: Movie${it.body}")
                    }
                    is DataState.Error -> {

                    }
                    is DataState.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
        }
        delay(2000)
        _state.value = DataState.Success("Success")
    }

    companion object {
        private const val TAG = "SplashViewModel"
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }

}