package com.example.elparking_test.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.elparking_test.R
import com.example.elparking_test.core.domain.Prediction
import com.example.elparking_test.databinding.FragmentPredictionDetailBinding
import com.example.elparking_test.presentation.viewmodel.PassPredictionCuriosityViewModel
import com.example.elparking_test.presentation.viewmodel.PassPredictionViewModel
import javax.inject.Inject

class PredictionDetailFragment: Fragment(R.layout.fragment_prediction_detail)  {
    private val viewModel: PassPredictionViewModel by activityViewModels()
    private val curiosityViewModel: PassPredictionCuriosityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPredictionDetailBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel.selectedPrediction.observe(this.viewLifecycleOwner, Observer<Prediction> {prediction ->
            binding.prediction = prediction
        })

        curiosityViewModel.getCuriosity().observe(this.viewLifecycleOwner, { curiosity ->
            binding.curiosityTextview.text = curiosity
        })
        return binding.root
    }
}