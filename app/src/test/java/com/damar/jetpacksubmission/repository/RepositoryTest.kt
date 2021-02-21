@file:Suppress("UNCHECKED_CAST")

package com.damar.jetpacksubmission.repository

import app.cash.turbine.test
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.*
import com.damar.jetpacksubmission.network.entity.*
import com.damar.jetpacksubmission.utils.DataState
import com.nhaarman.mockitokotlin2.any
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RepositoryTest {
    @RelaxedMockK private lateinit var localDao: LocalDao
    @RelaxedMockK private lateinit var remoteRepo: IMoviedbAPI
    @RelaxedMockK private lateinit var movieMapper: NetworkMapperMvDetail
    @RelaxedMockK private lateinit var tvMapper: NetworkMapperTvDetail
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        repository = Repository(localDao, remoteRepo, coroutineTestRule.testDispatcher, movieMapper, tvMapper)
    }

    @Test
    fun getTVTrendingUpdateSuccess() {
        val successBody: TvsTrendingNetworkEntity = mockk()
        val result: NetworkResponse<TvsTrendingNetworkEntity, Error> = NetworkResponse.Success(successBody)
        coEvery { remoteRepo.getTvTrending() } returns result
        coEvery { localDao.getTvTrending() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { result.body.results } returns mutableListOf()
            }
            repository.getTrendingTv(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { remoteRepo.getTvTrending() }
                coVerify { localDao.getTvTrending() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVTrendingGetSuccess() {
        coEvery { localDao.getTvTrending() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getTrendingTv(TxType.GET).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { localDao.getTvTrending() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVTrendingError() {
        val result: NetworkResponse<TvsTrendingNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getTvTrending() } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getTrendingTv(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getTvTrending() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVPopularUpdateSuccess() {
        val successBody: TvsPopularNetworkEntity = mockk()
        val result: NetworkResponse<TvsPopularNetworkEntity, Error> = NetworkResponse.Success(successBody)
        coEvery { remoteRepo.getTvPopular() } returns result
        coEvery { localDao.getTvPopular() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { result.body.results } returns mutableListOf()
            }
            repository.getPopularTv(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { remoteRepo.getTvPopular() }
                coVerify { localDao.getTvPopular() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVPopularGetSuccess() {
        coEvery { localDao.getTvPopular() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getPopularTv(TxType.GET).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { localDao.getTvPopular() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVPopularError() {
        val result: NetworkResponse<TvsPopularNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getTvPopular() } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getPopularTv(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getTvPopular() }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVDetailSuccess() {
        val successBody: DetailTvNetworkEntity = mockk()
        val result: NetworkResponse<DetailTvNetworkEntity, Error> = NetworkResponse.Success(successBody)
        val mappedResult: DetailTv = mockk()
        coEvery { remoteRepo.getTvDetail(any()) } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { tvMapper.mapFromEntity(result.body) } returns mappedResult
            }
            repository.getDetailTv(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mappedResult), expectItem())
                coVerify { remoteRepo.getTvDetail(any()) }
                expectComplete()
            }
        }
    }
    @Test
    fun getTVDetailError() {
        val result: NetworkResponse<DetailTvNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getTvDetail(any()) } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getDetailTv(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getTvDetail(any()) }
                expectComplete()
            }
        }
    }
    @Test
    fun getMovieTrendingUpdateSuccess() {
        val successBody: MoviesTrendingNetworkEntity = mockk()
        val result: NetworkResponse<MoviesTrendingNetworkEntity, Error> = NetworkResponse.Success(successBody)
        coEvery { remoteRepo.getMoviesTrending() } returns result
        coEvery { localDao.getMvTrending()} returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { result.body.results } returns mutableListOf()
            }
            repository.getTrendingMovie(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Movie>()), expectItem())
                coVerify { remoteRepo.getMoviesTrending() }
                coVerify { localDao.getMvTrending()}
                expectComplete()
            }
        }
    }
    @Test
    fun getMovieTrendingGetSuccess() {
        coEvery { localDao.getMvTrending() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getTrendingMovie(TxType.GET).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { localDao.getMvTrending() }
                expectComplete()
            }
        }
    }
    @Test
    fun getMovieTrendingError() {
        val result: NetworkResponse<MoviesTrendingNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getMoviesTrending()} returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getTrendingMovie(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getMoviesTrending()}
                expectComplete()
            }
        }
    }
    @Test
    fun getMoviePopularUpdateSuccess() {
        val successBody: MoviesPopularNetworkEntity = mockk()
        val result: NetworkResponse<MoviesPopularNetworkEntity, Error> = NetworkResponse.Success(successBody)
        coEvery { remoteRepo.getMoviesPopular() } returns result
        coEvery { localDao.getMvPopular()} returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { result.body.results } returns mutableListOf()
            }
            repository.getPopularMovie(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Movie>()), expectItem())
                coVerify { remoteRepo.getMoviesPopular() }
                coVerify { localDao.getMvPopular()}
                expectComplete()
            }
        }
    }
    @Test
    fun getMoviePopularGetSuccess() {
        coEvery { localDao.getMvPopular() } returns mutableListOf()
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getPopularMovie(TxType.GET).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mutableListOf<Tv>()), expectItem())
                coVerify { localDao.getMvPopular() }
                expectComplete()
            }
        }
    }
    @Test
    fun getMoviePopularError() {
        val result: NetworkResponse<MoviesPopularNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getMoviesPopular()} returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getPopularMovie(TxType.UPDATE).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getMoviesPopular()}
                expectComplete()
            }
        }
    }
    @Test
    fun getMovieDetailSuccess() {
        val successBody: DetailMvNetworkEntity = mockk()
        val result: NetworkResponse<DetailMvNetworkEntity, Error> = NetworkResponse.Success(successBody)
        val mappedResult: DetailMv = mockk()
        coEvery { remoteRepo.getMvDetail(any()) } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            if(result is NetworkResponse.Success){
                every { movieMapper.mapFromEntity(result.body) } returns mappedResult
            }
            repository.getDetailMovie(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Success(mappedResult), expectItem())
                coVerify { remoteRepo.getMvDetail(any()) }
                expectComplete()
            }
        }
    }
    @Test
    fun getMovieDetailError() {
        val result: NetworkResponse<DetailMvNetworkEntity, Error> = NetworkResponse.NetworkError(IOException("Network Error"))
        coEvery { remoteRepo.getMvDetail(any()) } returns result
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getDetailMovie(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(DataState.Error("Network Error"), expectItem())
                coVerify { remoteRepo.getMvDetail(any()) }
                expectComplete()
            }
        }
    }
}