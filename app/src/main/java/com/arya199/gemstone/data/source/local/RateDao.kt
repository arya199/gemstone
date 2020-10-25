package com.arya199.gemstone.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arya199.gemstone.data.Rate

@Dao
interface RateDao {

    @Query("SELECT * FROM rate_table")
    suspend fun getRates(): List<Rate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: Rate)
}