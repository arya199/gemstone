package com.arya199.gemstone.di

import android.content.Context
import androidx.room.Room
import com.arya199.gemstone.data.source.local.GemstoneDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(context: Context): GemstoneDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GemstoneDatabase::class.java,
            "Gemstone.db"
        ).build()
    }
}