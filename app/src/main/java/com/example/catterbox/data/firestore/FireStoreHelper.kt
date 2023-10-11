package com.example.catterbox.data.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.catterbox.data.database.model.MessageEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

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



    fun fetchMessageEntityFromFirestore(callback: (List<MessageEntity>) -> Unit) {
        firestore.collection("messages")
            .orderBy("created_at", Query.Direction.ASCENDING)
            .addSnapshotListener { value, _ ->
                val messages = value?.mapNotNull { document ->
                    document["message_content"]?.let { messageContent ->
                        MessageEntity(
                            id = 0,
                            post_user_name = document["post_user_name"].toString(),
                            message_content = messageContent.toString(),
                            created_at = document["created_at"].toString().toLong(),
                            room_id = 0
                        )
                    } ?: emptyList<MessageEntity>()[0]
                } ?: emptyList()
                callback(messages)
            }
    }

}
