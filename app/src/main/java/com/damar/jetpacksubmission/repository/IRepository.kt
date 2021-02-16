package com.damar.jetpacksubmission.repository

import com.damar.jetpacksubmission.models.*

interface IRepository {
    suspend fun getTvTrending(): List<TvTrending>
    suspend fun getTvPopular(): List<TvPopular>
    suspend fun getMvTrending(): List<MvTrending>
    suspend fun getMvPopular(): List<MvPopular>

    suspend fun getDetailTV(id : Int): DetailTv?
    suspend fun getDetailMV(id: Int): DetailMv?
}