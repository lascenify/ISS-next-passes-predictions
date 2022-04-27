package com.example.elparking_test.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elparking_test.core.domain.Prediction
import com.example.elparking_test.core.domain.PredictionParams
import com.example.elparking_test.core.domain.PredictionResponse
import com.example.elparking_test.network.PassPredictionAPIClient
import com.example.elparking_test.network.PassPredictionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassPredictionViewModel : ViewModel() {
    private var predictionParams: PredictionParams? = null
    private val service = PassPredictionAPIClient.getClient()!!.create(PassPredictionService::class.java)
    private val _selectedPrediction = MutableLiveData<Prediction>()
    val selectedPrediction: LiveData<Prediction>
        get() = _selectedPrediction
    private val predictions: MutableLiveData<PredictionResponse> by lazy {
        MutableLiveData<PredictionResponse>().also {
            loadPredictions()
        }
    }

    fun getPredictions(): LiveData<PredictionResponse> {
        return predictions
    }

    fun setPredictionParams(predictionParams: PredictionParams){
        this.predictionParams = predictionParams
        loadPredictions()
    }

    fun setSelectedPrediction(prediction: Prediction){
        _selectedPrediction.value = prediction
    }

    private fun loadPredictions() {
        if (predictionParams !== null) {
            service.getNextPassPredictions(
                lat = predictionParams!!.lat,
                lon = predictionParams!!.lon,
                alt = predictionParams!!.alt,
                n = 10
            ).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>
                ) {
                    if (response.body() !== null) {
                        val data = (response.body() as PredictionResponse)
                        predictions.value = data
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    //TODO
                }
            })
        }
    }
}