package com.damar.jetpacksubmission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.repository.Table
import com.damar.jetpacksubmission.ui.detail.viewmodel.DetailViewModel
import com.damar.jetpacksubmission.utils.DataState
import com.nhaarman.mockitokotlin2.any
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import java.io.IOException

@ExperimentalCoroutinesApi
class DetailViewModelTest{

    @RelaxedMockK private lateinit var repository: Repository
    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun getDetailMovieSuccess() {
        val movie: DetailMv = mockk()
        val movieFlow: Flow<DataState<DetailMv>> = flow {
            emit(DataState.Loading)
            emit(DataState.Success(movie))
        }
        coEvery { repository.getDetailMovie(anyInt()) } returns movieFlow

        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.getMovieDetail(anyInt())
            detailViewModel.detail.observeForever{
                if(it!=null && it is DataState.Success){
                    if(it.body is DetailMv){
                        assertEquals(it.body, movie)
                    }
                }
            }
            coVerify { repository.getDetailMovie(anyInt()) }
        }
    }
    @Test
    fun getDetailMovieError() {
        val error = IOException("Network Error")
        val movieFlow: Flow<DataState<DetailMv>> = flow {
            emit(DataState.Loading)
            emit(DataState.Error(error.message!!))
        }
        coEvery { repository.getDetailMovie(anyInt()) } returns movieFlow

        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.getMovieDetail(anyInt())
            detailViewModel.detail.observeForever{
                if(it!=null && it is DataState.Error){
                    assertEquals(it.e, error.message)
                }
            }
            coVerify { repository.getDetailMovie(anyInt()) }
        }
    }
    @Test
    fun getDetailTvSuccess() {
        val tv: DetailTv = mockk()
        val tvFlow: Flow<DataState<DetailTv>> = flow {
            emit(DataState.Loading)
            emit(DataState.Success(tv))
        }
        coEvery { repository.getDetailTv(anyInt()) } returns tvFlow

        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.getTvDetail(anyInt())
            detailViewModel.detail.observeForever{
                if(it!=null && it is DataState.Success){
                    if(it.body is DetailTv){
                        assertEquals(it.body, tv)
                    }
                }
            }
            coVerify { repository.getDetailTv(anyInt()) }
        }
    }
    @Test
    fun getDetailTvError() {
        val error = IOException("Network Error")
        val tvFlow: Flow<DataState<DetailTv>> = flow {
            emit(DataState.Loading)
            emit(DataState.Error(error.message!!))
        }
        coEvery { repository.getDetailTv(anyInt()) } returns tvFlow

        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.getTvDetail(anyInt())
            detailViewModel.detail.observeForever{
                if(it!=null && it is DataState.Error){
                    assertEquals(it.e, error.message)
                }
            }
            coVerify { repository.getDetailTv(anyInt()) }
        }
    }
    @Test
    fun isMovieFavouriteTrue(){
        coEvery { repository.isFavourite(any(), Table.FavMovie) } returns true
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = detailViewModel.isFavourite(any(), Table.FavMovie)
            coVerify { repository.isFavourite(any(), Table.FavMovie) }
            assertEquals(result, true)
        }
    }
    @Test
    fun isMovieFavouriteFalse(){
        coEvery { repository.isFavourite(any(), Table.FavMovie) } returns false
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = detailViewModel.isFavourite(any(), Table.FavMovie)
            coVerify { repository.isFavourite(any(), Table.FavMovie) }
            assertEquals(result, false)
        }
    }
    @Test
    fun isTvFavouriteTrue(){
        coEvery { repository.isFavourite(any(), Table.FavTv) } returns true
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = detailViewModel.isFavourite(any(), Table.FavTv)
            coVerify { repository.isFavourite(any(), Table.FavTv) }
            assertEquals(result, true)
        }
    }
    @Test
    fun isTvFavouriteFalse(){
        coEvery { repository.isFavourite(any(), Table.FavTv) } returns false
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = detailViewModel.isFavourite(any(), Table.FavTv)
            coVerify { repository.isFavourite(any(), Table.FavTv) }
            assertEquals(result, false)
        }
    }
    @Test
    fun insertMovie(){
        val item: DetailMv = mockk()
        coEvery { repository.insertFavourite(item, Table.FavMovie) } returns Unit
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.insertFavourite(item, Table.FavMovie)
            coVerify { repository.insertFavourite(item, Table.FavMovie) }
        }
    }
    @Test
    fun insertTv(){
        val item: DetailTv = mockk()
        coEvery { repository.insertFavourite(item, Table.FavTv) } returns Unit
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.insertFavourite(item, Table.FavTv)
            coVerify { repository.insertFavourite(item, Table.FavTv) }
        }
    }
    @Test
    fun deleteMovie(){
        coEvery { repository.deleteFavourite(anyInt(), Table.FavMovie) } returns Unit
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.deleteFavourite(anyInt(), Table.FavMovie)
            coVerify { repository.deleteFavourite(anyInt(), Table.FavMovie) }
        }
    }
    @Test
    fun deleteTv(){
        coEvery { repository.deleteFavourite(anyInt(), Table.FavTv) } returns Unit
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.deleteFavourite(anyInt(), Table.FavTv)
            coVerify { repository.deleteFavourite(anyInt(), Table.FavTv) }
        }
    }
}