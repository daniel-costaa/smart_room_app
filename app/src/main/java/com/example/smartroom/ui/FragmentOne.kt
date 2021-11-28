package com.example.smartroom.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartroom.R
import com.example.smartroom.common.Resource
import com.example.smartroom.databinding.FragmentOneBinding
import com.example.smartroom.network.FirebaseRepositoryImpl
import com.example.smartroom.viewmodel.SensorsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class FragmentOne : Fragment(R.layout.fragment_one) {
    private lateinit var binding: FragmentOneBinding
    private val sensorsViewModel = SensorsViewModel(FirebaseRepositoryImpl())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneBinding.bind(view)

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            sensorsViewModel.temperatureData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.temperatureData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.temperatureData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }

            sensorsViewModel.luminosityData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.luminosityData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.luminosityData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }

            sensorsViewModel.umidityData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.humidityData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.humidityData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }
        }
    }

    private fun showErrorMsg(state: Resource.Failed<Double>) {
        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
    }


    private fun nextPageListener() {
        binding.buttonNavigateToNextFragment.setOnClickListener {
            navigateToFragmentTwo()
        }
    }

    private fun navigateToFragmentTwo() {
        val fromFragmentOneToFragmentTwo =
            FragmentOneDirections.actionFragmentOneToFragmentTwo()

        findNavController().navigate(fromFragmentOneToFragmentTwo)
    }
}