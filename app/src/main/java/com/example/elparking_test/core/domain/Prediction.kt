package com.example.elparking_test.core.domain

import com.squareup.moshi.Json
import java.util.*

data class Prediction (
    val duration: Int,
    val durationInMinutes: Int,
    val durationInSeconds: Int,
    val timeLeft: Long,
    val dateOfPrediction: String
        )