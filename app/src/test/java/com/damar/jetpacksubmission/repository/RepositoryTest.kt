@file:Suppress("UNCHECKED_CAST")

package com.damar.jetpacksubmission.repository

import androidx.paging.PagingSource
import app.cash.turbine.test
import com.damar.jetpacksubmission.CoroutinesTestRule
import com.damar.jetpacksubmission.local.CacheMapperDetailMovieFav
import com.damar.jetpacksubmission.local.CacheMapperDetailTvFav
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
import com.damar.jetpacksubmission.local.entity.FavTvsEntity
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
    @RelaxedMockK private lateinit var cacheMovieMapper: CacheMapperDetailMovieFav
    @RelaxedMockK private lateinit var cacheTvMapper: CacheMapperDetailTvFav
    private lateinit var repository: Repository

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        repository = Repository(localDao, remoteRepo, coroutineTestRule.testDispatcher, movieMapper, tvMapper, cacheMovieMapper, cacheTvMapper)
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
    @Test
    fun getFavouritePagingMovie(){
        val mockResult: PagingSource<Int, FavMoviesEntity> = mockk()
        coEvery { localDao.favMoviesPagingSource() } returns mockResult
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getFavouritePaging()
            coVerify { localDao.favMoviesPagingSource() }
            assertEquals(result, mockResult)
        }
    }
    @Test
    fun getFavouritePagingTV(){
        val mockResult:  PagingSource<Int, FavTvsEntity> = mockk()
        coEvery { localDao.favTvsPagingSource() } returns mockResult
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.getFavouritePagingTv()
            coVerify { localDao.favTvsPagingSource() }
            assertEquals(result, mockResult)
        }
    }
    @Test
    fun isFavouriteMovieReturnTrue(){
        val list: List<FavMoviesEntity> = mockk()
        coEvery { localDao.getFavouriteMovies(any()) } returns list
        every { list.isEmpty() } returns false
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.isFavourite(any(), Table.FavMovie)
            coVerify { localDao.getFavouriteMovies(any()) }
            assertEquals(result, true)
        }
    }
    @Test
    fun isFavouriteMovieReturnFalse(){
        val list: List<FavMoviesEntity> = mockk()
        coEvery { localDao.getFavouriteMovies(any()) } returns list
        every { list.isEmpty() } returns true
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.isFavourite(any(), Table.FavMovie)
            coVerify { localDao.getFavouriteMovies(any()) }
            assertEquals(result, false)
        }
    }
    @Test
    fun isFavouriteTvReturnTrue(){
        val list: List<FavTvsEntity> = mockk()
        coEvery { localDao.getFavouriteTvs(any()) } returns list
        every { list.isEmpty() } returns false
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.isFavourite(any(), Table.FavTv)
            coVerify { localDao.getFavouriteTvs(any()) }
            assertEquals(result, true)
        }
    }
    @Test
    fun isFavouriteTvReturnFalse(){
        val list: List<FavTvsEntity> = mockk()
        coEvery { localDao.getFavouriteTvs(any()) } returns list
        every { list.isEmpty() } returns true
        coroutineTestRule.testDispatcher.runBlockingTest {
            val result = repository.isFavourite(any(), Table.FavTv)
            coVerify { localDao.getFavouriteTvs(any()) }
            assertEquals(result, false)
        }
    }
    @Test
    fun deleteFavouriteMovie(){
        coEvery { localDao.deleteFavMovies(any()) } returns Unit
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.deleteFavourite(any(), Table.FavMovie)
            coVerify { localDao.deleteFavMovies(any()) }
        }
    }
    @Test
    fun deleteFavouriteTv(){
        coEvery { localDao.deleteFavTv(any()) } returns Unit
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.deleteFavourite(any(), Table.FavTv)
            coVerify { localDao.deleteFavTv(any()) }
        }
    }
    @Test
    fun insertFavouriteMovie(){
        val item: DetailMv = mockk()
        val mappedItem : FavMoviesEntity = mockk()
        coEvery { localDao.insertFavMovie(any()) } returns Unit
        coEvery { cacheMovieMapper.mapToEntity(item) } returns mappedItem
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.insertFavourite(item, Table.FavMovie)
            coVerify { localDao.insertFavMovie(any()) }
        }
    }
    @Test
    fun insertFavouriteTv(){
        val item: DetailTv = mockk()
        val mappedItem: FavTvsEntity = mockk()
        coEvery { localDao.insertFavTv(any()) } returns Unit
        coEvery { cacheTvMapper.mapToEntity(item) } returns mappedItem
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.insertFavourite(item, Table.FavTv)
            coVerify { localDao.insertFavTv(any()) }
        }
    }
    @Test
    fun getSearchResultSuccess(){
        val successBody: SearchResultNetworkEntity = mockk()
        val body: NetworkResponse<SearchResultNetworkEntity, Error> = NetworkResponse.Success(successBody)
        coEvery { remoteRepo.searchItem(any()) } returns body
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getSearchResult(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(expectItem(), DataState.Success(successBody))
                coVerify { remoteRepo.searchItem(any()) }
                expectComplete()
            }
        }
    }
    @Test
    fun getSearchResultError(){
        val errorBody = IOException("Error Connection")
        val body: NetworkResponse<SearchResultNetworkEntity, Error> = NetworkResponse.NetworkError(errorBody)
        coEvery { remoteRepo.searchItem(any()) } returns body
        coroutineTestRule.testDispatcher.runBlockingTest {
            repository.getSearchResult(any()).test {
                assertEquals(DataState.Loading, expectItem())
                assertEquals(expectItem(), DataState.Error("${errorBody.message}"))
                coVerify { remoteRepo.searchItem(any()) }
                expectComplete()
            }
        }
    }
}