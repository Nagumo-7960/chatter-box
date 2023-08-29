package com.example.catterbox.firestore

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.catterbox.data.MessageData
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreHelper {

    private val firestore = FirebaseFirestore.getInstance()

    fun saveUserData(messageData: MessageData, context:Context) {
        // usersコレクションを指定し、userData.idを一意としたデータを作成し、setで保存する。
        firestore.collection("messages").document(messageData.id.toString())
            .set(messageData)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error saving message data: $e", Toast.LENGTH_LONG)
                    .show()
            }
    }
}