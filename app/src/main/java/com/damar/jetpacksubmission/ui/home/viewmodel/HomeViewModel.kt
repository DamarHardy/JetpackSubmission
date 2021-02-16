package com.damar.jetpacksubmission.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damar.jetpacksubmission.models.MvPopular
import com.damar.jetpacksubmission.models.MvTrending
import com.damar.jetpacksubmission.models.TvPopular
import com.damar.jetpacksubmission.models.TvTrending
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {
    private var _tvTrending = MutableLiveData<MutableList<TvTrending>>(mutableListOf())
    private var _mvTrending = MutableLiveData<MutableList<MvTrending>>(mutableListOf())
    private var _mvPopular = MutableLiveData<MutableList<MvPopular>>(mutableListOf())
    private var _tvPopular = MutableLiveData<MutableList<TvPopular>>(mutableListOf())

    val tvTrending: LiveData<MutableList<TvTrending>> get() = _tvTrending
    val mvTrending: LiveData<MutableList<MvTrending>> get() = _mvTrending
    val mvPopular : LiveData<MutableList<MvPopular>>  get() = _mvPopular
    val tvPopular : LiveData<MutableList<TvPopular>>  get() = _tvPopular

    private var _selectedTv = MutableLiveData<Int>()
    private var _selectedMv = MutableLiveData<Int>()
    val selectedTv: LiveData<Int> get() = _selectedTv
    val selectedMv: LiveData<Int> get() = _selectedMv

    fun setSelectedTv(id: Int){ _selectedTv.value = id}
    fun setSelectedMv(id: Int){ _selectedMv.value = id}
    fun nullSelectedTv() { _selectedTv.value = null}
    fun nullSelectedMv() { _selectedMv.value = null}


    fun loadData(){
        viewModelScope.launch {
            launch {
                _tvTrending.value = repository.getTvTrending()}
            launch {
                _tvPopular.value = repository.getTvPopular()}
            launch {
                _mvTrending.value = repository.getMvTrending()}
            launch {
                _mvPopular.value = repository.getMvPopular()}
        }
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }
    companion object {
        private const val TAG = "HomeViewModel"
    }
}