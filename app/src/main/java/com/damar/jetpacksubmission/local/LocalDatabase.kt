package com.damar.jetpacksubmission.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.damar.jetpacksubmission.local.entity.MoviesCacheEntity
import com.damar.jetpacksubmission.local.entity.TvsCacheEntity

@Database(entities = [MoviesCacheEntity::class, TvsCacheEntity::class], version = 5)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDAO(): LocalDao
    companion object{
        const val DATABASE_NAME = "localDB"
    }
}