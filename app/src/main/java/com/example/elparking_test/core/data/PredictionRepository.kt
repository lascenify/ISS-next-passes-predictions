package com.example.elparking_test.core.data

import com.example.elparking_test.network.PassPredictionService
import javax.inject.Inject

class PredictionRepository @Inject constructor(private val service: PassPredictionService) {

    /*fun getPrediction(
        lat: Double,
        lon: Double,
        alt: Double?
    ): LiveData<Resource<PredictionResponse>> {
        return service.getNextPassPredictions(lat, lon, alt, null)
    }*/
}