package com.damar.jetpacksubmission.di

import com.damar.jetpacksubmission.local.CacheMapperDetailMovieFav
import com.damar.jetpacksubmission.local.CacheMapperDetailTvFav
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.network.IMoviedbAPI
import com.damar.jetpacksubmission.network.NetworkMapperMvDetail
import com.damar.jetpacksubmission.network.NetworkMapperTvDetail
import com.damar.jetpacksubmission.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object Repository {
    @Singleton
    @Provides
    fun provideRepository(
            localRepo: LocalDao,
            remoteRepo: IMoviedbAPI,
    ): Repository{
        return Repository(localRepo,remoteRepo, Dispatchers.IO, NetworkMapperMvDetail, NetworkMapperTvDetail, CacheMapperDetailMovieFav, CacheMapperDetailTvFav)
    }
    @Singleton
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.IO
}