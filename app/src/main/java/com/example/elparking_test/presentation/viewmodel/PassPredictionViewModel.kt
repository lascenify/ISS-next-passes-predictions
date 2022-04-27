package com.example.elparking_test.presentation.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elparking_test.models.Prediction
import com.example.elparking_test.models.PredictionParams
import com.example.elparking_test.models.PredictionResponse
import com.example.elparking_test.network.PassPredictionAPIClient
import com.example.elparking_test.network.PassPredictionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class PassPredictionViewModel : ViewModel() {
    private var predictionParams: PredictionParams? = null
    private val service = PassPredictionAPIClient.getClient()!!.create(PassPredictionService::class.java)
    private val _selectedPrediction = MutableLiveData<Prediction>()
    val selectedPrediction: LiveData<Prediction>
        get() = _selectedPrediction
    private val predictions = MutableLiveData<PredictionResponse>()

    val predictionError = MutableLiveData(false)

    private val _locationError = MutableLiveData<Boolean>(false)
    val locationError: LiveData<Boolean>
        get() = _locationError

    private val _locationPermissionGranted = MutableLiveData<Boolean>(true)
    val locationPermissionGranted: LiveData<Boolean>
        get() = _locationPermissionGranted

    fun getPredictions(): LiveData<PredictionResponse> {
        return predictions
    }

    fun setPredictionParams(predictionParams: PredictionParams){
        this.predictionParams = predictionParams
        loadPredictions()
    }

    fun updateLocationError(locationErrorHappened: Boolean){
        _locationError.postValue(locationErrorHappened)
    }

    fun updateLocationPermissionGranted(permissionGranted: Boolean){
        _locationPermissionGranted.postValue(permissionGranted)
    }

    fun setSelectedPrediction(prediction: Prediction){
        _selectedPrediction.value = prediction
    }

    private fun loadPredictions() {
        if (predictionParams !== null) {
            service.getNextPassPredictions(
                lat = round(predictionParams!!.lat),
                lon = round(predictionParams!!.lon),
                alt = null,
                n = 10
            ).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>
                ) {
                    if (response.body() !== null) {
                        predictionError.postValue(false)
                        val data = (response.body() as PredictionResponse)
                        predictions.value = data
                    } else {
                        predictionError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    predictionError.postValue(true)
                }
            })
        }
    }
}