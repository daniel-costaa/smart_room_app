package com.example.smartroom.network

interface RemoteDatasource {
    fun setupListeners()
    fun fetchSensorData()

}