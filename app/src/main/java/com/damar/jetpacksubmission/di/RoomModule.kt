package com.damar.jetpacksubmission.di

import android.content.Context
import androidx.room.Room
import com.damar.jetpacksubmission.local.LocalDao
import com.damar.jetpacksubmission.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideLocalCache(@ApplicationContext context: Context): LocalDatabase{
        return Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                LocalDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
    @Singleton
    @Provides
    fun provideLocalDao(localDatabase: LocalDatabase): LocalDao{
        return localDatabase.getDAO()
    }
}