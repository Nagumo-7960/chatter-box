package com.example.catterbox.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catterbox.database.dao.UserDAO
import com.example.catterbox.database.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun userDao(): UserDAO
}