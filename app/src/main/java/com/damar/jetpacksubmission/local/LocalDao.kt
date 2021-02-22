package com.damar.jetpacksubmission.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
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
    @Query("SELECT * FROM FavoriteMovies")
    fun favMoviesPagingSource(): PagingSource<Int, FavMoviesEntity>
    @Query("SELECT * FROM FavoriteMovies WHERE id = :id")
    fun getFavouriteMovies(id: String): LiveData<List<FavMoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(item: FavMoviesEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(TvTrending: List<TvsCacheEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(TvTrending: List<MoviesCacheEntity>)

    @Query("DELETE FROM FavoriteMovies WHERE id = :id")
    suspend fun deleteFavMovies(id: String)

}