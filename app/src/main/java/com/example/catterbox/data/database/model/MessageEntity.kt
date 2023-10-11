package com.example.catterbox.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val post_user_name: String,
    val message_content:String,
    val created_at:Long,
    val room_id:Int
)