package com.example.elparking_test.models

data class Prediction (
    val duration: Int,
    val durationInMinutes: Int,
    val durationInSeconds: Int,
    val timeLeft: Long,
    val dateOfPrediction: String
)