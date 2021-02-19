package com.damar.jetpacksubmission.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.damar.jetpacksubmission.local.entity.*

@Database(entities = [MoviesCacheEntity::class, TvsCacheEntity::class], version = 5)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDAO(): LocalDao
    companion object{
        const val DATABASE_NAME = "localDB"
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            LocalDatabase::class.java,
                            "localDB"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}