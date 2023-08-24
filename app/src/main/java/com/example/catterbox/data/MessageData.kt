package com.example.catterbox.data

import androidx.room.PrimaryKey

data class MessageData(
    @PrimaryKey
    val id:Int,
    val post_user_id: Int,
    val message_content:String,
    val room_id:Int
)