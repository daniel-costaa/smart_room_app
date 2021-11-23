package com.example.smartroom.network

interface RemoteDatasource {
    fun setupListeners()
    fun fetchTemperatureData()
    fun fetchUmidityData()
    fun fetchLuminosityData()

}