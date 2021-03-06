package com.example.elparking_test.network

import com.example.elparking_test.utils.Constants.PassPredictionService.PARAM_ALT
import com.example.elparking_test.utils.Constants.PassPredictionService.PARAM_LAT
import com.example.elparking_test.utils.Constants.PassPredictionService.PARAM_LON
import com.example.elparking_test.utils.Constants.PassPredictionService.PARAM_N
import com.example.elparking_test.utils.Constants.PassPredictionService.PASS_PREDICTIONS
import com.example.elparking_test.models.PredictionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface PassPredictionService {
    @GET(PASS_PREDICTIONS)
    fun getNextPassPredictions(
        @Query(PARAM_LAT)
        lat:Double,
        @Query(PARAM_LON)
        lon:Double,
        @Query(PARAM_ALT)
        alt: Int?,
        @Query(PARAM_N)
        n: Int?
    ): Call<PredictionResponse>

}