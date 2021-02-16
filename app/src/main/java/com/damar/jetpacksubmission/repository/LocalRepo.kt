package com.damar.jetpacksubmission.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damar.jetpacksubmission.models.MvPopular
import com.damar.jetpacksubmission.models.MvTrending
import com.damar.jetpacksubmission.models.TvPopular
import com.damar.jetpacksubmission.models.TvTrending

@Dao
interface LocalRepo {
    @Query(value = "SELECT * FROM TvTrending")
    suspend fun getTvTrending(): List<TvTrending>
    @Query(value = "SELECT * FROM TvPopular")
    suspend fun getTvPopular(): List<TvPopular>
    @Query(value = "SELECT * FROM MvTrending")
    suspend fun getMvTrending(): List<MvTrending>
    @Query(value = "SELECT * FROM MvPopular")
    suspend fun getMvPopular(): List<MvPopular>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvTrending(TvTrending: List<TvTrending>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvPopular(TvTrending: List<TvPopular>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMvTrending(TvTrending: List<MvTrending>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMvPopular(TvTrending: List<MvPopular>)
}