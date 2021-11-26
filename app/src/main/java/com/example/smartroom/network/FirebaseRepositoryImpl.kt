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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FirebaseRepositoryImpl : FirebaseRepository {
    private val firebaseDatabaseReference: FirebaseDatabase by lazy {
        Firebase.database
    }

    override suspend fun getAllSensorData(): Flow<Resource<Double>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as Double
                trySend(Resource.success(data))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.failed(error.message))
            }
        }
        firebaseDatabaseReference.getReference("temperatura").addValueEventListener(listener)

        awaitClose {
            firebaseDatabaseReference.getReference("temperatura").removeEventListener(listener)
        }
    }
}