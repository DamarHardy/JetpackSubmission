package com.damar.jetpacksubmission.ui.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.repository.Table
import com.damar.jetpacksubmission.ui.favourite.viewmodel.FavouriteViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@ExperimentalCoroutinesApi
class FavouriteViewModelTest {
    @RelaxedMockK private lateinit var repository: Repository
    private lateinit var favViewModel: FavouriteViewModel

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun deleteMovie(){
        coEvery { repository.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavMovie) } returns Unit
        favViewModel = FavouriteViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            favViewModel.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavMovie)
            coVerify { repository.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavMovie) }
        }
    }
    @Test
    fun deleteTv(){
        coEvery { repository.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavTv) } returns Unit
        favViewModel = FavouriteViewModel(repository, coroutineTestRule.testDispatcher)
        coroutineTestRule.testDispatcher.runBlockingTest {
            favViewModel.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavTv)
            coVerify { repository.deleteFavourite(ArgumentMatchers.anyInt(), Table.FavTv) }
        }
    }
}