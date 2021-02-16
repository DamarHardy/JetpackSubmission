package com.damar.jetpacksubmission.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.damar.jetpacksubmission.models.MvPopular
import com.damar.jetpacksubmission.models.MvTrending
import com.damar.jetpacksubmission.models.TvPopular
import com.damar.jetpacksubmission.models.TvTrending
import com.damar.jetpacksubmission.repository.LocalRepo

@Database(entities = [TvTrending::class,TvPopular::class,MvTrending::class,MvPopular::class], version = 4)
abstract class LocalDatabase: RoomDatabase() {
    abstract val localRepo: LocalRepo
    companion object{
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