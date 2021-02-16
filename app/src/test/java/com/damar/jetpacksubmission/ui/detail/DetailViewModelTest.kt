package com.damar.jetpacksubmission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.detail.viewmodel.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

@ExperimentalCoroutinesApi
class DetailViewModelTest{

    @RelaxedMockK private lateinit var repository: Repository
    private lateinit var detailViewModel: DetailViewModel

    @MockK
    private lateinit var observerMv: Observer<Any>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun testGetDetailMv_returnDetailMv() {
        val detailMv: DetailMv = mockk()
        coEvery { repository.getDetailMv(anyInt()) } returns detailMv
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        detailViewModel.detail.observeForever(observerMv)

        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.detail.observeForever {
                detailViewModel.getDetailMv(anyInt())
                coVerify { repository.getDetailMv(anyInt()) }
                assertNotNull(it)
                assertEquals(detailMv, it)
            }
        }
    }
    @Test
    fun testGetDetailMv_returnNull() {
        coEvery { repository.getDetailMv(anyInt()) } returns null
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        detailViewModel.detail.observeForever(observerMv)

        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.detail.observeForever {
                detailViewModel.getDetailMv(anyInt())
                coVerify { repository.getDetailMv(anyInt()) }
                assertEquals(null, it)
            }
        }
    }

    @Test
    fun testGetDetailTv_returnDetailTv() {
        val detailTv: DetailTv = mockk()
        coEvery { repository.getDetailTv(anyInt()) } returns detailTv
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        detailViewModel.detail.observeForever(observerMv)

        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.detail.observeForever{
                detailViewModel.getDetailTv(anyInt())
                coVerify { repository.getDetailTv(anyInt()) }
                assertNotNull(detailViewModel.detail.value)
                assertEquals(detailTv, it)
            }
        }
    }
    @Test
    fun testGetDetailTv_returnNull() {
        coEvery { repository.getDetailTv(anyInt()) } returns null
        detailViewModel = DetailViewModel(repository, coroutineTestRule.testDispatcher)
        detailViewModel.detail.observeForever(observerMv)

        coroutineTestRule.testDispatcher.runBlockingTest {
            detailViewModel.detail.observeForever{
                detailViewModel.getDetailTv(anyInt())
                coVerify { repository.getDetailTv(anyInt()) }
                assertEquals(null, it)
            }
        }
    }
}