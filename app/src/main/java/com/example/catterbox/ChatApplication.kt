package com.example.catterbox


import android.app.Application
import com.example.catterbox.database.ChatDatabase

class ChatApplication : Application() {

    companion object {
        lateinit var chatDatabase: ChatDatabase
    }
}