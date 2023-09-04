package com.example.catterbox.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.catterbox.database.model.MessageEntity
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreHelper {

    private val firestore = FirebaseFirestore.getInstance()

    fun saveUserData(messageData: MessageEntity, context: Context) {
        // usersコレクションを指定し、ランダムなIDを割り当てて保存する。
        firestore.collection("messages")
            .add(messageData)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show()
                Log.d("added-message", messageData.toString())
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error saving message data: $e", Toast.LENGTH_LONG).show()
                Log.d("added-message", messageData.toString())
            }
    }
}