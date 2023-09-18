package com.example.catterbox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catterbox.data.database.dao.ChatRoomDAO
import com.example.catterbox.data.database.dao.MessageDAO
import com.example.catterbox.data.database.dao.UserDAO
import com.example.catterbox.data.database.model.ChatRoomEntity
import com.example.catterbox.data.database.model.MessageEntity
import com.example.catterbox.data.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MessageEntity::class,
        ChatRoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun messageDao(): MessageDAO
    abstract fun chatRoomDao(): ChatRoomDAO

}