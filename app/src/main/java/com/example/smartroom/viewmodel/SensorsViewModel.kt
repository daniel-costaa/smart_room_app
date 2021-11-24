package com.example.smartroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.common.Resource
import com.example.smartroom.network.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorsViewModel(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<Double>>(
        Resource.Success(0.0)
    )
    val state: StateFlow<Resource<Double>> get() = _state

    init {
        viewModelScope.launch {
            repository.getAllSensorData().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = Resource.success(resource.data)
                    }
                    is Resource.Failed -> {
                        _state.value = Resource.failed(resource.message)
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
}