package com.example.smartroom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.smartroom.R
import com.example.smartroom.databinding.FragmentTwoBinding

class FragmentTwo : Fragment(R.layout.fragment_two) {
    private lateinit var binding: FragmentTwoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTwoBinding.bind(view)
    }
}