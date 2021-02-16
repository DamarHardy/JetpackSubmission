package com.damar.jetpacksubmission.ui.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.MvPopular
import com.damar.jetpacksubmission.models.MvTrending
import com.damar.jetpacksubmission.models.TvPopular
import com.damar.jetpacksubmission.models.TvTrending
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var context: Context
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        repository = mockk(relaxed = true)
    }

    @Test
    fun loadData_successfullyRequest_return_emptyList() {
        coEvery { repository.getTvTrending() } returns mutableListOf()
        coEvery { repository.getTvPopular() } returns mutableListOf()
        coEvery { repository.getMvTrending() } returns mutableListOf()
        coEvery { repository.getMvPopular() } returns mutableListOf()

        homeViewModel = HomeViewModel(repository)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()
        }
        // Test : Make sure All Repository Function is called
        coVerify { repository.getTvTrending() }
        coVerify { repository.getTvPopular() }
        coVerify { repository.getMvTrending() }
        coVerify { repository.getMvPopular() }

        // Test: Make sure All Variables return emptyList
        assertEquals(mutableListOf<MvPopular>(), homeViewModel.mvPopular.value)
        assertEquals(mutableListOf<TvPopular>(), homeViewModel.tvPopular.value)
        assertEquals(mutableListOf<MvTrending>(), homeViewModel.mvTrending.value)
        assertEquals(mutableListOf<TvTrending>(), homeViewModel.tvTrending.value)
    }
    @Test
    fun loadData_successfullyRequest_returnList() {
        val tvTrending = mutableListOf(TvTrending(firstAirDate="2021-01-15", overview="Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.", originalLanguage="en", genreIds=null, posterPath="/glKDfE6btIRcVB5zrjspRIs4r52.jpg", originCountry=null, backdropPath="/57vVjteucIF3bGnZj6PmaoJRScw.jpg", mediaType="tv", voteAverage=8.5, originalName="WandaVision", popularity=2446.194, name="WandaVision", id=85271, voteCount=2345))
        val mvTrending = mutableListOf(MvTrending(overview="Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.", originalLanguage="en", originalTitle="Wonder Woman 1984", video=false, title="Wonder Woman 1984", genreIds=null, posterPath="/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg", backdropPath="/srYya1ZlI97Au4jUYAktDe3avyA.jpg", releaseDate="2020-12-16", mediaType="movie", voteAverage=7.1, popularity=2751.779, id=464052, adult=false, voteCount=3080))
        val tvPopular = mutableListOf(TvPopular(firstAirDate="2017-01-26", overview="Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.", originalLanguage="en", genreIds=null, posterPath="/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg", originCountry=null, backdropPath="/9hvhGtcsGhQY58pukw8w55UEUbL.jpg", popularity=2081.425, voteAverage=8.6, originalName="Riverdale", name="Riverdale", id=69050, voteCount=8676))
        val mvPopular = mutableListOf(MvPopular(overview="When a virus threatens to turn the now earth-dwelling friendly alien hybrids against humans, Captain Rose Corley must lead a team of elite mercenaries on a mission to the alien world in order to save what's left of humanity.", originalLanguage="en", originalTitle="Skylines", video=false, title="Skylines", genreIds=null, posterPath="/2W4ZvACURDyhiNnSIaFPHfNbny3.jpg", backdropPath="/3ombg55JQiIpoPnXYb2oYdr6DtP.jpg", releaseDate="2021-02-01", popularity=2826.445, voteAverage=0.0, id=560144, adult=false, voteCount=0))
        mockkObject(Repository)
        mockkObject(Repository.Companion)
        coEvery { repository.getTvTrending() } returns tvTrending
        coEvery { repository.getTvPopular() } returns tvPopular
        coEvery { repository.getMvTrending() } returns mvTrending
        coEvery { repository.getMvPopular() } returns mvPopular

        homeViewModel = HomeViewModel(repository)
        coroutineTestRule.testDispatcher.runBlockingTest {
            homeViewModel.loadData()
        }
        // Test : Make sure All Repository Function is called
        coVerify { repository.getTvTrending() }
        coVerify { repository.getTvPopular() }
        coVerify { repository.getMvTrending() }
        coVerify { repository.getMvPopular() }

        // Test: Make sure All Variables return emptyList
        assertEquals(mvPopular, homeViewModel.mvPopular.value)
        assertEquals(tvPopular, homeViewModel.tvPopular.value)
        assertEquals(mvTrending, homeViewModel.mvTrending.value)
        assertEquals(tvTrending, homeViewModel.tvTrending.value)
    }
    @Test
    fun tvTrending_getter_NotNullTest(){
        homeViewModel = HomeViewModel(repository)
        assertNotNull(homeViewModel.tvTrending.value)
    }
    @Test
    fun mvTrending_getter_NotNullTest(){
        homeViewModel = HomeViewModel(repository)
        assertNotNull(homeViewModel.mvTrending.value)
    }
    @Test
    fun tvPopular_getter_NotNullTest(){
        homeViewModel = HomeViewModel(repository)
        assertNotNull(homeViewModel.tvPopular.value)
    }
    @Test
    fun mvPopular_getter_NotNullTest(){
        homeViewModel = HomeViewModel(repository)
        assertNotNull(homeViewModel.mvPopular.value)
    }
}