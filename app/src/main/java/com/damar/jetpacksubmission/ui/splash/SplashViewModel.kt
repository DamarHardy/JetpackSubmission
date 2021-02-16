package com.damar.jetpacksubmission.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.detail.viewmodel.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val repository: Repository): ViewModel() {
    private var _state = MutableLiveData<State<Any>>()
    val state: LiveData<State<Any>> get() = _state
    suspend fun init(){
        _state.value = State.Loading()
        withContext(viewModelScope.coroutineContext) {
            launch {
                repository.getTvTrending()}
            launch {
                repository.getTvPopular()}
            launch {
                repository.getMvTrending()}
            launch {
                repository.getMvPopular()}
        }
        delay(2000)
        _state.value = State.Success("Success")
    }
}