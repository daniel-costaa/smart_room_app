package com.example.smartroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.common.Resource
import com.example.smartroom.network.FirebaseDatasource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensorsViewModel : ViewModel() {
    private val firebaseDatasource: FirebaseDatasource = FirebaseDatasource()
    private var temperatureData : Double = 0.0
    private val _temperatureState = MutableStateFlow(
        Resource.success(temperatureData)
    )
    val temperatureState: StateFlow<Resource<Double>> get() = _temperatureState

    fun initializeListeners() {
        firebaseDatasource.setupListeners()
    }

    fun fetchTemperatureData() {
        viewModelScope.launch {
            _temperatureState.value = Resource.loading(null)

            val temperature = firebaseDatasource.temperatura
            temperature.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _temperatureState.value = Resource.success(snapshot.value as Double)
                }

                override fun onCancelled(error: DatabaseError) {
                    _temperatureState.value = Resource.error("Erro ${error.message}", null)
                }
            })
        }
    }
}