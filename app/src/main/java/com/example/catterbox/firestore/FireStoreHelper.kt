package com.example.catterbox.firestore

import com.example.catterbox.data.MessageData
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreHelper {

    private val firestore = FirebaseFirestore.getInstance()

    fun saveUserData(messageData: MessageData) {
        // usersコレクションを指定し、userData.idを一意としたデータを作成し、setで保存する。
        firestore.collection("messages").document(messageData.id.toString())
            .set(messageData)
            .addOnSuccessListener {
                println("message data saved successfully")
            }
            .addOnFailureListener { e ->
                println("Error saving message data: $e")
            }
    }
}