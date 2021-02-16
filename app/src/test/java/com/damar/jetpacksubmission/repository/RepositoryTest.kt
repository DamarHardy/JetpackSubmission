package com.damar.jetpacksubmission.repository

import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.models.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class RepositoryTest {
    @RelaxedMockK private lateinit var localRepo: LocalRepo
    @RelaxedMockK private lateinit var remoteRepo: RemoteRepo
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        repository = Repository(localRepo, remoteRepo, coroutineTestRule.testDispatcher)
    }

    @Test
    fun getTvTrending_localNotEmpty_returnLocal() {
        val tvTrending = mutableListOf(
            TvTrending(
                firstAirDate = "2021-01-15",
                overview = "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
                originalLanguage = "en",
                genreIds = null,
                posterPath = "/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
                originCountry = null,
                backdropPath = "/57vVjteucIF3bGnZj6PmaoJRScw.jpg",
                mediaType = "tv",
                voteAverage = 8.5,
                originalName = "WandaVision",
                popularity = 2446.194,
                name = "WandaVision",
                id = 85271,
                voteCount = 2345
            )
        )
        val result = mutableListOf<TvTrending>()
        coEvery { localRepo.getTvTrending() } returns tvTrending as List<TvTrending>
        coEvery { remoteRepo.getTvTrending() } returns emptyList()

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvTrending())
        }
        coVerify { localRepo.getTvTrending() }
        coVerify(exactly = 0) { remoteRepo.getTvTrending()}
        assertEquals(tvTrending, result)
    }
    @Test
    fun getTvTrending_localEmpty_returnNetwork() {
        val tvTrending = mutableListOf(
            TvTrending(
                firstAirDate = "2021-01-15",
                overview = "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
                originalLanguage = "en",
                genreIds = null,
                posterPath = "/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
                originCountry = null,
                backdropPath = "/57vVjteucIF3bGnZj6PmaoJRScw.jpg",
                mediaType = "tv",
                voteAverage = 8.5,
                originalName = "WandaVision",
                popularity = 2446.194,
                name = "WandaVision",
                id = 85271,
                voteCount = 2345
            )
        )
        val result = mutableListOf<TvTrending>()
        coEvery { localRepo.getTvTrending() } returns emptyList()
        coEvery { remoteRepo.getTvTrending() } returns tvTrending
        coEvery { localRepo.insertTvTrending(tvTrending as List<TvTrending>) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvTrending())
        }
        coVerify { localRepo.getTvTrending() }
        coVerify { remoteRepo.getTvTrending()}
        coVerify { localRepo.insertTvTrending(tvTrending as List<TvTrending>) }
        assertEquals(tvTrending, result)
    }
    @Test
    fun getTvTrending_returnEmpty() {
        val result = mutableListOf<TvTrending>()
        coEvery { localRepo.getTvTrending() } returns emptyList()
        coEvery { remoteRepo.getTvTrending() } returns emptyList()
        coEvery { localRepo.insertTvTrending(emptyList()) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvTrending())
        }
        coVerify { localRepo.getTvTrending() }
        coVerify { remoteRepo.getTvTrending()}
        coVerify(exactly = 0) { localRepo.insertTvTrending(emptyList()) }

        assertEquals(emptyList<TvTrending>(), result)
    }

    @Test
    fun getTvPopular_localNotEmpty_returnLocal() {
        val tvPopular = mutableListOf(
            TvPopular(
                firstAirDate = "2017-01-26",
                overview = "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                originalLanguage = "en",
                genreIds = null,
                posterPath = "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                originCountry = null,
                backdropPath = "/9hvhGtcsGhQY58pukw8w55UEUbL.jpg",
                popularity = 2081.425,
                voteAverage = 8.6,
                originalName = "Riverdale",
                name = "Riverdale",
                id = 69050,
                voteCount = 8676
            )
        )
        val result = mutableListOf<TvPopular>()
        coEvery { localRepo.getTvPopular() } returns tvPopular

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvPopular())
        }
        coVerify { localRepo.getTvPopular() }
        coVerify(exactly = 0) { remoteRepo.getTvPopular()}
        assertEquals(tvPopular, result)
    }
    @Test
    fun getTvPopular_localEmpty_returnNetwork() {
        val tvPopular = mutableListOf(
            TvPopular(
                firstAirDate = "2017-01-26",
                overview = "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                originalLanguage = "en",
                genreIds = null,
                posterPath = "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                originCountry = null,
                backdropPath = "/9hvhGtcsGhQY58pukw8w55UEUbL.jpg",
                popularity = 2081.425,
                voteAverage = 8.6,
                originalName = "Riverdale",
                name = "Riverdale",
                id = 69050,
                voteCount = 8676
            )
        )
        val result = mutableListOf<TvPopular>()
        coEvery { localRepo.getTvPopular() } returns emptyList()
        coEvery { remoteRepo.getTvPopular() } returns tvPopular
        coEvery { localRepo.insertTvPopular(tvPopular as List<TvPopular>) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvPopular())
        }
        coVerify { localRepo.getTvPopular()}
        coVerify { remoteRepo.getTvPopular()}
        coVerify { localRepo.insertTvPopular(tvPopular as List<TvPopular>) }
        assertEquals(tvPopular, result)
    }
    @Test
    fun getTvPopular_returnEmpty() {
        val result = mutableListOf<TvPopular>()
        coEvery { localRepo.getTvPopular() } returns emptyList()
        coEvery { remoteRepo.getTvPopular() } returns emptyList()
        coEvery { localRepo.insertTvPopular(emptyList()) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getTvPopular())
        }
        coVerify { localRepo.getTvPopular() }
        coVerify { remoteRepo.getTvPopular()}
        coVerify(exactly = 0) { localRepo.insertTvPopular(emptyList()) }
        assertEquals(emptyList<TvPopular>(), result)
    }

    @Test
    fun getMvTrending_localNotEmpty_returnLocal() {
        val mvTrending = mutableListOf(
            MvTrending(
                overview = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                originalLanguage = "en",
                originalTitle = "Wonder Woman 1984",
                video = false,
                title = "Wonder Woman 1984",
                genreIds = null,
                posterPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                backdropPath = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                releaseDate = "2020-12-16",
                mediaType = "movie",
                voteAverage = 7.1,
                popularity = 2751.779,
                id = 464052,
                adult = false,
                voteCount = 3080
            )
        )
        val result = mutableListOf<MvTrending>()
        coEvery { localRepo.getMvTrending() } returns mvTrending

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvTrending())
        }
        coVerify { localRepo.getMvTrending() }
        coVerify(exactly = 0) { remoteRepo.getMvTrending()}
        assertEquals(mvTrending, result)
    }
    @Test
    fun getMvTrending_localEmpty_returnNetwork() {
        val mvTrending = mutableListOf(
            MvTrending(
                overview = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                originalLanguage = "en",
                originalTitle = "Wonder Woman 1984",
                video = false,
                title = "Wonder Woman 1984",
                genreIds = null,
                posterPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                backdropPath = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                releaseDate = "2020-12-16",
                mediaType = "movie",
                voteAverage = 7.1,
                popularity = 2751.779,
                id = 464052,
                adult = false,
                voteCount = 3080
            )
        )
        val result = mutableListOf<MvTrending>()
        coEvery { localRepo.getMvTrending() } returns emptyList()
        coEvery { remoteRepo.getMvTrending() } returns mvTrending
        coEvery { localRepo.insertMvTrending(mvTrending as List<MvTrending>) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvTrending())
        }
        coVerify { localRepo.getMvTrending() }
        coVerify { remoteRepo.getMvTrending()}
        coVerify { localRepo.insertMvTrending(mvTrending as List<MvTrending>) }
        assertEquals(mvTrending, result)
    }
    @Test
    fun getMvTrending_returnEmpty() {
        val result = mutableListOf<MvTrending>()
        coEvery { localRepo.getMvTrending() } returns emptyList()
        coEvery { remoteRepo.getMvTrending() } returns emptyList()
        coEvery { localRepo.insertMvTrending(emptyList()) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvTrending())
        }
        coVerify { localRepo.getMvTrending() }
        coVerify { remoteRepo.getMvTrending()}
        coVerify(exactly = 0) { localRepo.insertMvTrending(emptyList()) }
        assertEquals(emptyList<MvTrending>(), result)
    }

    @Test
    fun getMvPopular_localNotEmpty_returnLocal() {
        val mvPopular = mutableListOf(
            MvPopular(
                overview = "When a virus threatens to turn the now earth-dwelling friendly alien hybrids against humans, Captain Rose Corley must lead a team of elite mercenaries on a mission to the alien world in order to save what's left of humanity.",
                originalLanguage = "en",
                originalTitle = "Skylines",
                video = false,
                title = "Skylines",
                genreIds = null,
                posterPath = "/2W4ZvACURDyhiNnSIaFPHfNbny3.jpg",
                backdropPath = "/3ombg55JQiIpoPnXYb2oYdr6DtP.jpg",
                releaseDate = "2021-02-01",
                popularity = 2826.445,
                voteAverage = 0.0,
                id = 560144,
                adult = false,
                voteCount = 0
            )
        )
        val result = mutableListOf<MvPopular>()
        coEvery { localRepo.getMvPopular() } returns mvPopular

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvPopular())
        }
        coVerify { localRepo.getMvPopular() }
        coVerify(exactly = 0) { remoteRepo.getMvPopular()}
        assertEquals(mvPopular, result)
    }
    @Test
    fun getMvPopular_localEmpty_returnNetwork() {
        val mvPopular = mutableListOf(
            MvPopular(
                overview = "When a virus threatens to turn the now earth-dwelling friendly alien hybrids against humans, Captain Rose Corley must lead a team of elite mercenaries on a mission to the alien world in order to save what's left of humanity.",
                originalLanguage = "en",
                originalTitle = "Skylines",
                video = false,
                title = "Skylines",
                genreIds = null,
                posterPath = "/2W4ZvACURDyhiNnSIaFPHfNbny3.jpg",
                backdropPath = "/3ombg55JQiIpoPnXYb2oYdr6DtP.jpg",
                releaseDate = "2021-02-01",
                popularity = 2826.445,
                voteAverage = 0.0,
                id = 560144,
                adult = false,
                voteCount = 0
            )
        )
        val result = mutableListOf<MvPopular>()
        coEvery { localRepo.getMvPopular() } returns emptyList()
        coEvery { remoteRepo.getMvPopular() } returns mvPopular
        coEvery { localRepo.insertMvPopular(mvPopular as List<MvPopular>) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvPopular())
        }
        coVerify { localRepo.getMvPopular()}
        coVerify { remoteRepo.getMvPopular()}
        coVerify { localRepo.insertMvPopular(mvPopular as List<MvPopular>) }
        assertEquals(mvPopular, result)
    }
    @Test
    fun getMvPopular_returnEmpty() {
        val result = mutableListOf<MvPopular>()
        coEvery { localRepo.getMvPopular() } returns emptyList()
        coEvery { remoteRepo.getMvPopular() } returns emptyList()
        coEvery { localRepo.insertMvPopular(emptyList()) } returns Unit

        coroutineTestRule.testDispatcher.runBlockingTest {
            result.addAll(repository.getMvPopular())
        }
        coVerify { localRepo.getMvPopular() }
        coVerify { remoteRepo.getMvPopular()}
        coVerify(exactly = 0) { localRepo.insertMvPopular(emptyList()) }
        assertEquals(emptyList<MvPopular>(), result)
    }

    @Test
    fun getDetailMV_returnDetailMv(){
        val detailMv : DetailMv = mockk()
        coEvery { remoteRepo.getDetailMV(anyInt()) } returns detailMv

        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getDetailMv(anyInt())

            coVerify { remoteRepo.getDetailMV(anyInt()) }
            assertNotNull(result)
            assertEquals(detailMv, result)
        }
    }
    @Test
    fun getDetailMV_returnNull(){
        coEvery { remoteRepo.getDetailMV(anyInt()) } returns null

        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getDetailMv(anyInt())

            coVerify { remoteRepo.getDetailMV(anyInt()) }
            assertEquals(null, result)
        }
    }
    @Test
    fun getDetailTV_returnDetailTv(){
        val detailTv : DetailTv = mockk()
        coEvery { remoteRepo.getDetailTV(anyInt()) } returns detailTv

        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getDetailTv(anyInt())

            coVerify { remoteRepo.getDetailTV(anyInt()) }
            assertNotNull(result)
            assertEquals(detailTv, result)
        }
    }
    @Test
    fun getDetailTV_returnNull(){
        coEvery { remoteRepo.getDetailTV(anyInt()) } returns null

        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getDetailTv(anyInt())

            coVerify { remoteRepo.getDetailTV(anyInt()) }
            assertEquals(null, result)
        }
    }
}