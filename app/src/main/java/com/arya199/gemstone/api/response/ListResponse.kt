package com.arya199.gemstone.api.response

data class ListResponse(
    var success: Boolean,
    var terms: String,
    var privacy: String,
    var currencies: Map<String, String>
)