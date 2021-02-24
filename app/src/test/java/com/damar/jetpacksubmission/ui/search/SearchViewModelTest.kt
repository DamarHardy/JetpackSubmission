package com.damar.jetpacksubmission.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.network.entity.SearchResultNetworkEntity
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.search.viewmodel.SearchViewModel
import com.damar.jetpacksubmission.utils.DataState
import com.nhaarman.mockitokotlin2.any
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @RelaxedMockK private lateinit var repository: Repository
    private lateinit var viewModel: SearchViewModel

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp(){
        MockKAnnotations.init(this, true, true)
        viewModel = SearchViewModel(repository, coroutineTestRule.testDispatcher)

    }
    @Test
    fun getSearchResult() {
        val body: SearchResultNetworkEntity = mockk()
        val flow = flow{
            emit(DataState.Loading)
            emit(DataState.Success(body))
        }
        coEvery { repository.getSearchResult(any()) } returns flow
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getSearchResult(any())

            coVerify { repository.getSearchResult(any()) }
            viewModel.movieList.observeForever{
                if(it!=null && it is DataState.Success){
                    assertEquals(viewModel.movieList.value, DataState.Success(body))
                }
            }
            viewModel.personList.observeForever{
                if(it!=null && it is DataState.Success){
                    assertEquals(viewModel.personList.value, DataState.Success(body))
                }
            }
            viewModel.tvList.observeForever{
                if(it!=null && it is DataState.Success){
                    assertEquals(viewModel.tvList.value, DataState.Success(body))
                }
            }
        }
    }
}