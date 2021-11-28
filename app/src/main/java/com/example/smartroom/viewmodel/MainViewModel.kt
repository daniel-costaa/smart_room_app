package com.example.smartroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.common.Resource
import com.example.smartroom.network.FirebaseRepository
import com.example.smartroom.network.FirebaseRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {
    private val repository: FirebaseRepository = FirebaseRepositoryImpl()

    private val _temperatureData = MutableStateFlow<Resource<Double>>(Resource.Loading())
    val temperatureData: StateFlow<Resource<Double>> get() = _temperatureData

    private val _umidityData = MutableStateFlow<Resource<Double>>(Resource.Loading())
    val umidityData: StateFlow<Resource<Double>> get() = _umidityData

    private val _luminosityData = MutableStateFlow<Resource<Double>>(Resource.Loading())
    val luminosityData: StateFlow<Resource<Double>> get() = _luminosityData

    init {
        viewModelScope.launch {
            getSensorData(TEMPERATURA_PATH, _temperatureData)
        }

        viewModelScope.launch {
            getSensorData(UMIDADE_PATH, _umidityData)
        }

        viewModelScope.launch {
            getSensorData(LUMINOSIDADE_PATH, _luminosityData)
        }
    }

    private suspend fun getSensorData(
        path: String,
        stateFlow: MutableStateFlow<Resource<Double>>
    ) {
        repository.getSensorData(path).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    stateFlow.value = Resource.success(resource.data)
                }
                is Resource.Failed -> {
                    stateFlow.value = Resource.failed(resource.message)
                }
                is Resource.Loading -> stateFlow.value = Resource.loading()
            }
        }
    }

    companion object {
        private const val UMIDADE_PATH = "/umidade"
        private const val TEMPERATURA_PATH = "/temperatura"
        private const val LUMINOSIDADE_PATH = "/luminosidade"
    }
}


