package com.damar.jetpacksubmission.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
        private val repository: Repository,
        private val savedStateHandle: SavedStateHandle
        ): ViewModel() {
    private var _tvTrending = MutableLiveData<DataState<List<Tv>>>()
    private var _mvTrending = MutableLiveData<DataState<List<Movie>>>()
    private var _mvPopular = MutableLiveData<DataState<List<Movie>>>()
    private var _tvPopular = MutableLiveData<DataState<List<Tv>>>()

    val tvTrending: LiveData<DataState<List<Tv>>> get() = _tvTrending
    val mvTrending: LiveData<DataState<List<Movie>>> get() = _mvTrending
    val mvPopular : LiveData<DataState<List<Movie>>>  get() = _mvPopular
    val tvPopular : LiveData<DataState<List<Tv>>>  get() = _tvPopular

    private var _selectedTv = MutableLiveData<Int?>()
    private var _selectedMv = MutableLiveData<Int?>()
    val selectedTv: LiveData<Int?> get() = _selectedTv
    val selectedMv: LiveData<Int?> get() = _selectedMv

    fun setSelectedTv(id: Int){ _selectedTv.value = id }
    fun setSelectedMovie(id: Int){ _selectedMv.value = id }

    fun nullSelectedTv() { _selectedTv.value = null }
    fun nullSelectedMv() { _selectedMv.value = null }


    fun loadData(){
        viewModelScope.launch(viewModelScope.coroutineContext) {
            repository.getPopularTv().onEach {
                when(it){
                    is DataState.Success -> {
                        println("Popular TV: ${it.body}")
                        _tvPopular.value = it
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
                        _tvTrending.value = it
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
                        _mvPopular.value = it
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
                        _mvTrending.value = it
                    }
                    is DataState.Error -> {

                    }
                    is DataState.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }
    companion object {
        private const val TAG = "HomeViewModel"
    }
}