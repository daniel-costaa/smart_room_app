package com.example.smartroom.network

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDatasource : RemoteDatasource {
    private val firebaseDatabaseReference: FirebaseDatabase by lazy {
        Firebase.database
    }

    lateinit var umidade: DatabaseReference
    lateinit var temperatura: DatabaseReference
    lateinit var luminosidade: DatabaseReference

    override fun setupListeners() {
        umidade = firebaseDatabaseReference.getReference(UMIDADE_PATH)
        temperatura = firebaseDatabaseReference.getReference(TEMPERATURA_PATH)
        luminosidade = firebaseDatabaseReference.getReference(LUMINOSIDADE_PATH)
    }


    companion object {
        private const val UMIDADE_PATH = "/umidade"
        private const val TEMPERATURA_PATH = "/temperatura"
        private const val LUMINOSIDADE_PATH = "/luminosidade"
    }
}