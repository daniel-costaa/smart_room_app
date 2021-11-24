package com.example.smartroom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartroom.R
import com.example.smartroom.common.Resource
import com.example.smartroom.databinding.FragmentOneBinding
import com.example.smartroom.network.FirebaseRepositoryImpl
import com.example.smartroom.viewmodel.SensorsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FragmentOne : Fragment(R.layout.fragment_one) {
    private lateinit var binding: FragmentOneBinding
    private val sensorsViewModel: SensorsViewModel
        get() = SensorsViewModel(FirebaseRepositoryImpl())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneBinding.bind(view)

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            sensorsViewModel.state.collect { state ->
                when(state) {
                    is Resource.Success -> {
                        binding.temperatureData.text = state.data.toString()
                    }
                }
            }
        }
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