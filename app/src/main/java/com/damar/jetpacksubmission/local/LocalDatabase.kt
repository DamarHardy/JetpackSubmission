package com.damar.jetpacksubmission.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
import com.damar.jetpacksubmission.local.entity.FavTvsEntity
import com.damar.jetpacksubmission.local.entity.MoviesCacheEntity
import com.damar.jetpacksubmission.local.entity.TvsCacheEntity

@Database(entities = [MoviesCacheEntity::class,
    TvsCacheEntity::class,
    FavMoviesEntity::class,
    FavTvsEntity::class], version = 6)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDAO(): LocalDao
    companion object{
        const val DATABASE_NAME = "localDB"
    }
}