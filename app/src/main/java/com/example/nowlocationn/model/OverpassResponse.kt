package com.example.nowlocationn.model

data class OverpassResponse(
    val elements: List<OverpassElement>
)

data class OverpassElement(
    val id: Long,
    val lat: Double?,
    val lon: Double?,
    val center: OverpassCenter?,
    val tags: Map<String, String>?
)

data class OverpassCenter(
    val lat: Double?,
    val lon: Double?
)