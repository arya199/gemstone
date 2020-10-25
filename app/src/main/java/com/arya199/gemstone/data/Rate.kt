package com.arya199.gemstone.data

import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName="rate_table", primaryKeys = ["from", "to"])
data class Rate(
    var from: String,
    var to: String,
    var rate: Double?,
    @Ignore var fullText: String = ""
) {
    constructor(from: String, to: String, rate: Double): this(from, to, rate, "")
}