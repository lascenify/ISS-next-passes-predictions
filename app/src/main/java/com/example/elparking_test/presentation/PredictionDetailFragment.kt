package com.example.elparking_test.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.elparking_test.R
import com.example.elparking_test.models.Prediction
import com.example.elparking_test.databinding.FragmentPredictionDetailBinding
import com.example.elparking_test.presentation.viewmodel.PassPredictionCuriosityViewModel
import com.example.elparking_test.presentation.viewmodel.PassPredictionViewModel

class PredictionDetailFragment: Fragment(R.layout.fragment_prediction_detail)  {
    private val viewModel: PassPredictionViewModel by activityViewModels()
    private val curiosityViewModel: PassPredictionCuriosityViewModel by activityViewModels()
    private lateinit var binding: FragmentPredictionDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPredictionDetailBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel.selectedPrediction.observe(this.viewLifecycleOwner, Observer<Prediction> {prediction ->
            binding.prediction = prediction
            curiosityViewModel.setNumber(prediction.duration)
            getCuriosity()
        })


        return binding.root
    }

    private fun getCuriosity(){
        curiosityViewModel.getCuriosity().observe(this.viewLifecycleOwner) { curiosity ->
            binding.curiosityTextview.text =
                "${binding.prediction?.duration.toString()}${getString(R.string.duration_description)}$curiosity"
        }
    }
}