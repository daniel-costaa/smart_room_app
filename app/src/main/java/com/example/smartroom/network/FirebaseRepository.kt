package com.example.smartroom.network

import com.example.smartroom.common.Resource
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun getAllSensorData(): Flow<Resource<Double>>
}