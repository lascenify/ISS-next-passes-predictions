package com.example.elparking_test

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.elparking_test.databinding.ActivityMainBinding
import com.example.elparking_test.models.PredictionParams
import com.example.elparking_test.presentation.viewmodel.PassPredictionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

const val DEFAULT_LAT = 40.4381311
const val DEFAULT_LON = -3.8196218
class ScrollingActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: PassPredictionViewModel by viewModels()
    private lateinit var geocoder: Geocoder
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale("es", "ES"))
        checkLocationPermissions()
    }

    @SuppressLint("MissingPermission")
    // Suppressing it because the permission is checked in isPermissionGranted function
    private fun checkLocationPermissions(){
        val requestPermissionLauncher = createPermissionLauncherManager()
        if (isPermissionDenied()){
            viewModel.updateLocationPermissionGranted(false)
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            return
        } else {
            viewModel.updateLocationPermissionGranted(true)
            fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
                if (lastLocation !== null) {
                    viewModel.updateLocationError(false)
                    createViewModel(
                        lastLocation.latitude,
                        lastLocation.longitude
                    )
                } else {
                    viewModel.updateLocationError(true)
                    createViewModel(DEFAULT_LAT, DEFAULT_LON)
                }
            }
            fusedLocationClient.lastLocation.addOnFailureListener {
                viewModel.updateLocationError(true)
            }
        }
    }

    @SuppressLint("MissingPermission")
    // Suppressing it because the permission is checked in isPermissionGranted function
    private fun createPermissionLauncherManager(): ActivityResultLauncher<String> {
        return registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.updateLocationPermissionGranted(true)
                val lastLocation = fusedLocationClient.lastLocation.result
                createViewModel(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            } else {
                viewModel.updateLocationPermissionGranted(false)
                createViewModel(DEFAULT_LAT, DEFAULT_LON)
            }
        }
    }

    private fun isPermissionDenied(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun createViewModel(latitude: Double, longitude: Double){
        val address = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )?.get(0)?.getAddressLine(0)
        binding.locationTextview.text = "Estás en $address"
        viewModel.setPredictionParams(PredictionParams(
            lat = latitude,
            lon = longitude
        ))
    }
}