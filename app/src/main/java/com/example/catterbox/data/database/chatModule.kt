package com.example.catterbox.data.database

import android.app.Application
import androidx.room.Room
import com.example.catterbox.data.database.dao.MessageDAO
import com.example.catterbox.data.database.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    fun provideChatDatabase(application: Application): ChatDatabase {
        return Room.databaseBuilder(
            application,
            ChatDatabase::class.java,
            "chat-database"
        ).build()
    }

    @Provides
    fun provideMessageDao(database: ChatDatabase): MessageDAO {
        return database.messageDao()
    }

    @Provides
    fun provideUserDao(database: ChatDatabase): UserDAO {
        return database.userDao()
    }
}