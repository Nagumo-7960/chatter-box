package com.example.catterbox


import android.app.Application
import androidx.room.Room
import com.example.catterbox.database.ChatDatabase
import com.example.catterbox.database.dao.MessageDAO

class ChatApplication : Application() {

    companion object {
        lateinit var chatDatabase: ChatDatabase
        lateinit var dao: MessageDAO
    }

    override fun onCreate() {
        super.onCreate()
        chatDatabase = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java,
            "chat-database"
        ).build()
        dao = chatDatabase.messageDao()
    }
}