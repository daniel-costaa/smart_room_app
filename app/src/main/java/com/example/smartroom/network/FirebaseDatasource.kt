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
        umidade = firebaseDatabaseReference.getReference("/umidade")
        temperatura = firebaseDatabaseReference.getReference("/umidade")
        luminosidade = firebaseDatabaseReference.getReference("/umidade")
    }

    override fun getListenerData() {
    }
}