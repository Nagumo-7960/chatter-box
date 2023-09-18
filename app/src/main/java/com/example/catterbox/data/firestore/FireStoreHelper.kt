package com.example.catterbox.data.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.catterbox.data.database.model.MessageEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireStoreHelper {

    private val firestore = FirebaseFirestore.getInstance()

    fun saveUserData(messageData: MessageEntity, context: Context) {
        // usersコレクションを指定し、ランダムなIDを割り当てて保存する。
        firestore.collection("messages").add(messageData).addOnSuccessListener {
                Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show()
                Log.d("added-message", messageData.toString())
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Error saving message data: $e", Toast.LENGTH_LONG).show()
                Log.d("added-message", messageData.toString())
            }
    }



    fun fetchMessagesFromFirestore(callback: (List<String>) -> Unit) {
        firestore.collection("messages")
            .orderBy("created_at", Query.Direction.ASCENDING)
            .addSnapshotListener { value, _ ->
                val messages = value?.documents?.mapNotNull { document ->
                    document.getString("message_content")
                } ?: emptyList()
                callback(messages)
            }
    }

}
