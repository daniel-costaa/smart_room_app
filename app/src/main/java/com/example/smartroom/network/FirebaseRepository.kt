package com.example.smartroom.network

import com.example.smartroom.common.Resource
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun getTemperatureData(path: String): Flow<Resource<Double>>
    suspend fun getUmidityData(path: String): Flow<Resource<Double>>
    suspend fun getLuminosityData(path: String): Flow<Resource<Double>>
}