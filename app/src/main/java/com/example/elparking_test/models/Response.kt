package com.example.elparking_test.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Response (
    @field:Json(name= "duration")
    val duration: Double,
    @field:Json(name= "risetime")
    val risetime: Double,
)