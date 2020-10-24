package com.arya199.gemstone.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate

@Database(entities = [Currency::class, Rate::class], exportSchema = false, version = 1)
abstract class GemstoneDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}