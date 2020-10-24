package com.arya199.gemstone.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey var code: String,
    var fullText: String
)