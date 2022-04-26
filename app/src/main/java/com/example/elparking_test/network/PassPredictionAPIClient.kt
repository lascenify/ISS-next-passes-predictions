package com.example.elparking_test.network

import com.example.elparking_test.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object PassPredictionAPIClient {
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit? {
        val client: OkHttpClient = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.PassPredictionService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }
}