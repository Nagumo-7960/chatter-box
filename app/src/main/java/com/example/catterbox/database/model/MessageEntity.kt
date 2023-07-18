package com.example.catterbox.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val post_user_id: Int,
    val message_content:String,
    val room_id:Int
)