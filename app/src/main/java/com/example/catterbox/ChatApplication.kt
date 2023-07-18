package com.example.catterbox


import android.app.Application
import androidx.room.Room
import com.example.catterbox.database.ChatDatabase

class ChatApplication : Application() {

    companion object {
        lateinit var chatDatabase: ChatDatabase
    }

    override fun onCreate() {
        super.onCreate()
        chatDatabase = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java,
            "chat-database"
        ).build()
    }
}