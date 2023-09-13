package com.example.catterbox


import android.app.Application
import androidx.room.Room
import com.example.catterbox.data.database.ChatDatabase
import com.example.catterbox.data.database.dao.MessageDAO
import com.example.catterbox.data.database.dao.UserDAO

class ChatApplication : Application() {

    companion object {
        lateinit var chatDatabase: ChatDatabase
        lateinit var messageDao: MessageDAO
        lateinit var userDao: UserDAO
    }

    override fun onCreate() {
        super.onCreate()
        chatDatabase = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java,
            "chat-database"
        ).build()
        messageDao = chatDatabase.messageDao()
        userDao = chatDatabase.userDao()
    }
}