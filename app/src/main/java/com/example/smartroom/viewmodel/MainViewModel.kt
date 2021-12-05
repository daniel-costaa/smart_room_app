package com.example.smartroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartroom.common.Resource
import com.example.smartroom.network.FirebaseRepository
import com.example.smartroom.network.FirebaseRepositoryImpl
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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

    private val umidityListOfValues = mutableListOf<Entry>()
    private val _umidityDataSet = MutableStateFlow(LineDataSet(umidityListOfValues, "TESTE"))
    val umidityDataSet = _umidityDataSet

    private val luminosityListOfValues = mutableListOf<Entry>()
    private val _luminosityDataSet = MutableStateFlow(LineDataSet(luminosityListOfValues, "TESTE"))
    val luminosityDataSet = _luminosityDataSet

    private val temperatureListOfValues = mutableListOf<Entry>()
    private val _temperatureDataSet =
        MutableStateFlow(LineDataSet(temperatureListOfValues, "TESTE"))
    val temperatureDataSet = _temperatureDataSet

    init {
        viewModelScope.launch {
            getSensorData(
                TEMPERATURA_PATH,
                _temperatureData,
                temperatureListOfValues,
                _temperatureDataSet
            )
        }

        viewModelScope.launch {
            getSensorData(UMIDADE_PATH, _umidityData, umidityListOfValues, _umidityDataSet)
        }

        viewModelScope.launch {
            getSensorData(
                LUMINOSIDADE_PATH,
                _luminosityData,
                luminosityListOfValues,
                _luminosityDataSet
            )
        }
    }

    private suspend fun getSensorData(
        path: String,
        stateFlow: MutableStateFlow<Resource<Double>>,
        listOfValues: MutableList<Entry>,
        dataset: MutableStateFlow<LineDataSet>
    ) {
        repository.getSensorData(path).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    listOfValues.add(Entry(listOfValues.size.toFloat(), resource.data.toFloat()))
                    dataset.value = LineDataSet(listOfValues, "TESTE")
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


