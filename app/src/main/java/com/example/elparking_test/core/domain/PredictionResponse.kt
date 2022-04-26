package com.example.elparking_test.core.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PredictionResponse(
    @Json(name= "message")
    val message: String?,

    @Json(name= "request")
    val request: Request?,

    @Json(name= "response")
    val response: Array<Response>?,
    )