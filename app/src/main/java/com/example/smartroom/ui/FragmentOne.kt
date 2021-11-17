package com.example.smartroom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartroom.R
import com.example.smartroom.databinding.FragmentOneBinding


class FragmentOne : Fragment(R.layout.fragment_one) {
    private lateinit var binding: FragmentOneBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneBinding.bind(view)

        setupListeners()
    }

    private fun setupListeners() {
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