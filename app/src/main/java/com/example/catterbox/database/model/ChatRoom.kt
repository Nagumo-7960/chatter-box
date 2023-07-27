package com.example.catterbox.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name: String,
    val user_id:Int,
    val message_id:Int
)