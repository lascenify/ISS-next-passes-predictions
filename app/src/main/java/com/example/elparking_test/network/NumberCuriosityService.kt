package com.example.elparking_test.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface NumberCuriosityService {
    @Headers("Content-Type: text/plain; charset=UTF-8")
    @GET("{number}")
    fun getCuriosity(
        @Path("number")
        number: Int
    ): Call<String>
}