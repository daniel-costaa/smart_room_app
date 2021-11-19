package com.example.smartroom.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartroom.R
import com.example.smartroom.databinding.FragmentOneBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FragmentOne : Fragment(R.layout.fragment_one) {
    private lateinit var binding: FragmentOneBinding
    private val firebaseDatabaseReference: FirebaseDatabase by lazy {
        Firebase.database
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneBinding.bind(view)

        val umidade = firebaseDatabaseReference.getReference("/umidade")
        val temperatura = firebaseDatabaseReference.getReference("/temperatura")
        val luminosidade = firebaseDatabaseReference.getReference("/luminosidade")

        setupListeners(umidade, temperatura, luminosidade)
    }

    private fun setupListeners(umidade: DatabaseReference, temperatura: DatabaseReference, luminosidade: DatabaseReference) {
        binding.buttonNavigateToNextFragment.setOnClickListener {
            navigateToFragmentTwo()
        }

        firebaseListener(umidade, binding.humidityData)
        firebaseListener(temperatura, binding.temperatureData)
        firebaseListener(luminosidade, binding.luminosityData)
    }

    private fun firebaseListener(reference: DatabaseReference, textView: TextView) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textView.text = (snapshot.value as Double).toString()
            }

            override fun onCancelled(error: DatabaseError) {
                showToastError(error)
            }
        })
    }

    private fun showToastError(error: DatabaseError) {
        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToFragmentTwo() {
        val fromFragmentOneToFragmentTwo =
            FragmentOneDirections.actionFragmentOneToFragmentTwo()

        findNavController().navigate(fromFragmentOneToFragmentTwo)
    }
}