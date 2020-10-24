package com.arya199.gemstone.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arya199.gemstone.data.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_table")
    suspend fun getCurrencies(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: Currency)
}