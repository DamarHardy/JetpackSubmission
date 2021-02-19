package com.damar.jetpacksubmission.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import com.damar.jetpacksubmission.utils.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var handle : SavedStateHandle
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mockk()
        handle = mockk()
    }

    @Test
    fun loadDataReturnSuccess() {
        //--Build Fake Flow --
        val listTv : List<Tv> = mockk()
        val listMovie: List<Movie> = mockk()
        val tvFlow: Flow<DataState<List<Tv>>> = flow{
            emit(DataState.Loading)
            emit(DataState.Success(listTv))
        }
        val movieFlow: Flow<DataState<List<Movie>>> = flow{
            emit(DataState.Loading)
            emit(DataState.Success(listMovie))
        }
        coEvery { repository.getPopularTv() } returns tvFlow
        coEvery { repository.getTrendingTv() } returns tvFlow
        coEvery { repository.getPopularMovie() } returns movieFlow
        coEvery { repository.getTrendingMovie() } returns movieFlow

        homeViewModel = HomeViewModel(repository, handle)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()

            // Test : Make sure All Repository Function is called
            coVerify { repository.getPopularTv() }
            coVerify { repository.getTrendingTv() }
            coVerify { repository.getPopularMovie() }
            coVerify { repository.getTrendingMovie() }

            // Confirm the LiveData return desired Value
            homeViewModel.mvPopular.observeForever {
                if(it!=null && it is DataState.Success){
                    assertEquals(listMovie, it.body)
                }
            }
            homeViewModel.mvTrending.observeForever {
                if(it!=null && it is DataState.Success){
                    assertEquals(listMovie, it.body)
                }
            }
            homeViewModel.tvPopular.observeForever {
                if(it!=null && it is DataState.Success){
                    assertEquals(listTv, it.body)
                }
            }
            homeViewModel.tvTrending.observeForever {
                if(it!=null && it is DataState.Success){
                    assertEquals(listTv, it.body)
                }
            }
        }
    }
    @Test
    fun loadDataReturnError() {
        //--Build Fake Flow --
        val error = IOException("Network Error")
        val tvFlow: Flow<DataState<List<Tv>>> = flow{
            emit(DataState.Loading)
            emit(DataState.Error(error.message!!))
        }
        val movieFlow: Flow<DataState<List<Movie>>> = flow{
            emit(DataState.Loading)
            emit(DataState.Error(error.message!!))
        }
        coEvery { repository.getPopularTv() } returns tvFlow
        coEvery { repository.getTrendingTv() } returns tvFlow
        coEvery { repository.getPopularMovie() } returns movieFlow
        coEvery { repository.getTrendingMovie() } returns movieFlow

        homeViewModel = HomeViewModel(repository, handle)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()

            // Test : Make sure All Repository Function is called
            coVerify { repository.getPopularTv() }
            coVerify { repository.getTrendingTv() }
            coVerify { repository.getPopularMovie() }
            coVerify { repository.getTrendingMovie() }

            // Confirm the LiveData return desired Value
            homeViewModel.mvPopular.observeForever {
                if(it!=null && it is DataState.Error){
                    assertEquals(error.message, it.e)
                }
            }
            homeViewModel.mvTrending.observeForever {
                if(it!=null && it is DataState.Error){
                    assertEquals(error.message, it.e)
                }
            }
            homeViewModel.tvPopular.observeForever {
                if(it!=null && it is DataState.Error){
                    assertEquals(error.message, it.e)
                }
            }
            homeViewModel.tvTrending.observeForever {
                if(it!=null && it is DataState.Error){
                    assertEquals(error.message, it.e)
                }
            }
        }
    }
}