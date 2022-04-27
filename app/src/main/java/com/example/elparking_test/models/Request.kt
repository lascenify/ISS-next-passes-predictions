package com.example.elparking_test.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Request (
    @field:Json(name= "altitude")
    val altitude: Double,
    @field:Json(name= "datetime")
    val datetime: Double,
    @field:Json(name= "latitude")
    val latitude: Double,
    @field:Json(name= "longitude")
    val longitude: Double,
    @field:Json(name= "passes")
    val passes: Double
)