package com.example.elparking_test.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.elparking_test.R
import com.example.elparking_test.core.domain.PredictionParams
import com.example.elparking_test.databinding.FragmentPredictionListBinding
import com.example.elparking_test.presentation.viewmodel.PassPredictionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class PredictionListFragment: Fragment(R.layout.fragment_prediction_list) {
    private lateinit var predictionsAdapter: PredictionRecyclerViewAdapter
    private val viewModel: PassPredictionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPredictionListBinding.inflate(
            inflater,
            container,
            false
        )

        predictionsAdapter = PredictionRecyclerViewAdapter(R.layout.fragment_prediction_list_item) { prediction ->
            viewModel.setSelectedPrediction(prediction)
            val action = PredictionListFragmentDirections.actionMainToDetailFragment()
            findNavController().navigate(action)
        }
        binding.recyclerViewPredictions.adapter = predictionsAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPredictions()
    }


    private fun getPredictions(){
        viewModel.getPredictions().observe(requireActivity()) { predictions ->
            if (predictions !== null) {
                predictionsAdapter.apply {
                    update(predictions.response!!)
                }
            }
        }
    }
}