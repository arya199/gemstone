package com.arya199.gemstone.api.response

data class LiveResponse(
    var success: Boolean,
    var terms: String,
    var privacy: String,
    var timestamp: Long,
    var source: String,
    var quotes: Map<String, Double>
)