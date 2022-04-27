package com.example.elparking_test.api

import android.location.Geocoder
import androidx.test.platform.app.InstrumentationRegistry
import com.example.elparking_test.models.PredictionParams
import com.example.elparking_test.network.PassPredictionService
import com.example.elparking_test.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class PassPredictionServiceTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var service: PassPredictionService
    private lateinit var moshi: Moshi
    private lateinit var client: OkHttpClient
    @Before
    fun createService(){
        client = OkHttpClient().newBuilder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        service = Retrofit.Builder()
            .baseUrl(Constants.PassPredictionService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(PassPredictionService::class.java)
    }

    @Test
    fun assertGetting10Predictions(){
        val geocoder = Geocoder(appContext, Locale("es", "ES"))
        val address = geocoder.getFromLocation(
            40.0,
            -0.5,
            1
        )?.get(0)?.getAddressLine(0)
        val params = PredictionParams(40.0, -0.5, null)
        val forecast = service.getNextPassPredictions(params.lat, params.lon, params.alt, 10).execute()
        MatcherAssert.assertThat(forecast, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(forecast?.body()?.response?.size, `is`(10))
    }
}