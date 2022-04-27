package com.example.elparking_test.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elparking_test.network.NumberCuriosityAPIClient
import com.example.elparking_test.network.NumberCuriosityService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassPredictionCuriosityViewModel : ViewModel() {
    private val service = NumberCuriosityAPIClient.getClient()!!.create(NumberCuriosityService::class.java)
    private var _param: Int = 0
    private val curiosity = MutableLiveData<String>()

    fun getCuriosity(): LiveData<String> {
        return curiosity
    }

    fun setNumber(number: Int){
        _param = number
        loadCuriosity()
    }

    private fun loadCuriosity() {
        service.getCuriosity(_param).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() !== null) {
                    val data = (response.body() as String)
                    curiosity.postValue(data)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                curiosity.postValue("")
            }
        })
    }
}