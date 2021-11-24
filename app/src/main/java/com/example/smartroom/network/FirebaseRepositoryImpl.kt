package com.example.smartroom.network

import com.example.smartroom.common.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl : FirebaseRepository {


    private val firebaseDatabaseReference: FirebaseDatabase by lazy {
        Firebase.database
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllSensorData(): Flow<Resource<Double>> = callbackFlow {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as Double
                this@callbackFlow.trySendBlocking(Resource.success(data))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Resource.failed(error.message))
            }
        }
        firebaseDatabaseReference.getReference("temperatura").addValueEventListener(listener)

        awaitClose { firebaseDatabaseReference.getReference("temperatura").removeEventListener(listener) }
    }
}