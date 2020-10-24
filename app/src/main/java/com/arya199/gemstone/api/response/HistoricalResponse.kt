package com.arya199.gemstone.api.response

data class HistoricalResponse (
    var success: Boolean,
    var terms: String,
    var privacy: String,
    var historical: Boolean,
    var date: String,
    var timestamp: Double,
    var source: String,
    var quotes: Map<String, Double>
)