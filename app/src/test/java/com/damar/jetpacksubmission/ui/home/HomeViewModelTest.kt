package com.damar.jetpacksubmission.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.repository.TxType
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
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mockk()
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
        coEvery { repository.getPopularTv(TxType.GET) } returns tvFlow
        coEvery { repository.getTrendingTv(TxType.GET) } returns tvFlow
        coEvery { repository.getPopularMovie(TxType.GET) } returns movieFlow
        coEvery { repository.getTrendingMovie(TxType.GET) } returns movieFlow

        homeViewModel = HomeViewModel(repository)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()

            // Test : Make sure All Repository Function is called
            coVerify { repository.getPopularTv(TxType.GET) }
            coVerify { repository.getTrendingTv(TxType.GET) }
            coVerify { repository.getPopularMovie(TxType.GET) }
            coVerify { repository.getTrendingMovie(TxType.GET) }

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
        coEvery { repository.getPopularTv(TxType.GET) } returns tvFlow
        coEvery { repository.getTrendingTv(TxType.GET) } returns tvFlow
        coEvery { repository.getPopularMovie(TxType.GET) } returns movieFlow
        coEvery { repository.getTrendingMovie(TxType.GET) } returns movieFlow

        homeViewModel = HomeViewModel(repository)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()

            // Test : Make sure All Repository Function is called
            coVerify { repository.getPopularTv(TxType.GET) }
            coVerify { repository.getTrendingTv(TxType.GET) }
            coVerify { repository.getPopularMovie(TxType.GET) }
            coVerify { repository.getTrendingMovie(TxType.GET) }

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