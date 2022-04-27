package com.example.elparking_test

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.elparking_test.models.PredictionParams
import com.example.elparking_test.databinding.ActivityMainBinding
import com.example.elparking_test.presentation.viewmodel.PassPredictionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
const val DEFAULT_LAT = 40.0
const val DEFAULT_LON = -0.5
class ScrollingActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: PassPredictionViewModel by viewModels()
    private lateinit var geocoder: Geocoder
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale("es", "ES"))
        checkLocationPermissions()
    }

    private fun checkLocationPermissions(){
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    val lastLocation = fusedLocationClient.lastLocation.result
                    createViewModel(
                        lastLocation.latitude,
                        lastLocation.longitude,
                        lastLocation.altitude
                    )
                } else {
                    createViewModel(DEFAULT_LAT, DEFAULT_LON, null)
                }
            }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            return
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
                if (lastLocation !== null) {
                    createViewModel(
                        lastLocation.latitude,
                        lastLocation.longitude,
                        lastLocation.altitude
                    )
                } else {
                    createViewModel(DEFAULT_LAT, DEFAULT_LON, null)
                }
            }
        }
    }

    private fun createViewModel(latitude: Double, longitude: Double, altitude: Double?){
        val address = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )[0].getAddressLine(0)
        binding.navigationToolbar.title = "Estás en $address"
        viewModel.setPredictionParams(PredictionParams(
            lat = latitude,
            lon = longitude,
            alt = altitude
        ))
    }
}