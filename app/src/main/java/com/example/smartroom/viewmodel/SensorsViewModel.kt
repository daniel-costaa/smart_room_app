package com.example.smartroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.common.Resource
import com.example.smartroom.network.FirebaseRepository
import com.example.smartroom.network.FirebaseRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorsViewModel : ViewModel() {
    private val repository: FirebaseRepository = FirebaseRepositoryImpl()

    private val _temperatureData = MutableStateFlow<Resource<Double>>(Resource.Success(0.0))
    val temperatureData: StateFlow<Resource<Double>> get() = _temperatureData

    private val _umidityData = MutableStateFlow<Resource<Double>>(Resource.Success(0.0))
    val umidityData: StateFlow<Resource<Double>> get() = _umidityData

    private val _luminosityData = MutableStateFlow<Resource<Double>>(Resource.Success(0.0))
    val luminosityData: StateFlow<Resource<Double>> get() = _luminosityData

    init {
        viewModelScope.launch {
            repository.getSensorData(TEMPERATURA_PATH).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _temperatureData.value = Resource.success(resource.data)
                    }
                    is Resource.Failed -> {
                        _temperatureData.value = Resource.failed(resource.message)
                    }
                    is Resource.Loading -> _temperatureData.value = Resource.loading()
                }
            }

            repository.getSensorData(UMIDADE_PATH).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _umidityData.value = Resource.success(resource.data)
                    }
                    is Resource.Failed -> {
                        _umidityData.value = Resource.failed(resource.message)
                    }
                    is Resource.Loading -> _umidityData.value = Resource.loading()
                }
            }

            repository.getSensorData(LUMINOSIDADE_PATH).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _luminosityData.value = Resource.success(resource.data)
                    }
                    is Resource.Failed -> {
                        _luminosityData.value = Resource.failed(resource.message)
                    }
                    is Resource.Loading -> _luminosityData.value = Resource.loading()
                }
            }
        }
    }

    companion object {
        private const val UMIDADE_PATH = "/umidade"
        private const val TEMPERATURA_PATH = "/temperatura"
        private const val LUMINOSIDADE_PATH = "/luminosidade"
    }
}


