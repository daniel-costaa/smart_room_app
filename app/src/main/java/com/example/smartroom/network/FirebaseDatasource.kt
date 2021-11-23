package com.example.smartroom.network

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDatasource : RemoteDatasource {
    private val firebaseDatabaseReference: FirebaseDatabase by lazy {
        Firebase.database
    }

    private lateinit var umidade: DatabaseReference
    private lateinit var temperatura: DatabaseReference
    private lateinit var luminosidade: DatabaseReference

    override fun setupListeners() {
        umidade = firebaseDatabaseReference.getReference(UMIDADE_PATH)
        temperatura = firebaseDatabaseReference.getReference(TEMPERATURA_PATH)
        luminosidade = firebaseDatabaseReference.getReference(LUMINOSIDADE_PATH)
    }

    override fun fetchSensorData() {
    }

    companion object {
        private const val UMIDADE_PATH = "/umidade"
        private const val TEMPERATURA_PATH = "/temperatura"
        private const val LUMINOSIDADE_PATH = "/luminosidade"
    }
}