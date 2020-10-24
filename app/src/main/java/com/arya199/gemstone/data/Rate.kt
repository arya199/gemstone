package com.arya199.gemstone.data

import androidx.room.Entity

@Entity(tableName="rate_table", primaryKeys = ["from", "to"])
data class Rate(
    var from: String,
    var to: String,
    var rate: Double?
)