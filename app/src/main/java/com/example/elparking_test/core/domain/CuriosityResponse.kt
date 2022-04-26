package com.example.elparking_test.core.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CuriosityResponse(
    val rawJson: String
)