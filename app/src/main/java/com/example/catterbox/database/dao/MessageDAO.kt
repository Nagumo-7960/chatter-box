package com.example.catterbox.database.dao

import androidx.room.*
import com.example.catterbox.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDAO {
    @Query("select * from MessageEntity order by id desc")
    fun getAll(): Flow<List<MessageEntity>>

    //UIスレッドで実行できないためsuspend関数にする
    @Insert
    suspend fun create(messageEntity : MessageEntity)

    @Update
    suspend fun update(messageEntity: MessageEntity)

    @Delete
    suspend fun delete(messageEntity: MessageEntity)

    @Query("DELETE FROM MessageEntity")
    suspend fun deleteAll()
}