package com.example.smartroom.network

import com.example.smartroom.common.Resource
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun getSensorData(path: String): Flow<Resource<Double>>
}