package com.damar.jetpacksubmission.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damar.jetpacksubmission.local.entity.MoviesCacheEntity
import com.damar.jetpacksubmission.local.entity.TvsCacheEntity

@Dao
interface LocalDao {
    @Query(value = "SELECT * FROM Tv WHERE category = 'Trending'")
    suspend fun getTvTrending(): List<TvsCacheEntity>
    @Query(value = "SELECT * FROM Tv WHERE category = 'Popular'")
    suspend fun getTvPopular(): List<TvsCacheEntity>
    @Query(value = "SELECT * FROM Movies WHERE category = 'Trending'")
    suspend fun getMvTrending(): List<MoviesCacheEntity>
    @Query(value = "SELECT * FROM Movies WHERE category = 'Popular'")
    suspend fun getMvPopular(): List<MoviesCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(TvTrending: List<TvsCacheEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(TvTrending: List<MoviesCacheEntity>)
}