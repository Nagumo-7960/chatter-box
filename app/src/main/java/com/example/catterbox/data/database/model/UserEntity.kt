package com.example.catterbox.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id:String,
    val name: String
)