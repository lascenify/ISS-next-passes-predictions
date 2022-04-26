package com.example.elparking_test.network

import com.example.elparking_test.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

internal object NumberCuriosityAPIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        val client: OkHttpClient = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.NumberCuriosityService.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }
}