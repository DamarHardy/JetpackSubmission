package com.damar.jetpacksubmission.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damar.jetpacksubmission.network.entity.SearchResultsItem
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.utils.DataState
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private var _movieList = MutableLiveData<DataState<List<SearchResultsItem>>>()
    private var _personList =  MutableLiveData<DataState<List<SearchResultsItem>>>()
    private var _tvList = MutableLiveData<DataState<List<SearchResultsItem>>>()
    private var job: Job = SupervisorJob()

    val movieList : LiveData<DataState<List<SearchResultsItem>>> get() = _movieList
    val personList : LiveData<DataState<List<SearchResultsItem>>> get() = _personList
    val tvList : LiveData<DataState<List<SearchResultsItem>>> get() = _tvList

    fun getSearchResult(query: String){
        // Do it 3 times because it will be evaluated in 3 different places later
        EspressoIdlingResource.increment()
        EspressoIdlingResource.increment()
        EspressoIdlingResource.increment()
        job = viewModelScope.launch(dispatcher) {
            repository.getSearchResult(query).onEach {
                when(it){
                    is DataState.Error -> println(it.e)
                    is DataState.Loading -> println("Search Loading")
                    is DataState.Success -> {
                        val result = it.body
                        _movieList.value = DataState.Success(result.results?.filter { item -> item.mediaType ==  "movie"} ?: mutableListOf())
                        _personList.value = DataState.Success(result.results?.filter { item -> item.mediaType == "person" } ?: mutableListOf())
                        _tvList.value = DataState.Success(result.results?.filter { item -> item.mediaType == "tv"} ?: mutableListOf())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    fun getJob() = job
}