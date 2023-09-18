package com.example.catterbox.data.database.dao

import androidx.room.*
import com.example.catterbox.data.database.model.ChatRoomEntity
import com.example.catterbox.data.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDAO {
    @Query("select * from ChatRoomEntity order by id desc")
    fun getAll(): Flow<List<ChatRoomEntity>>

    //UIスレッドで実行できないためsuspend関数にする
    @Insert
    suspend fun create(chatRoomEntity : ChatRoomEntity)

    @Update
    suspend fun update(chatRoomEntity: ChatRoomEntity)

    @Delete
    suspend fun delete(chatRoomEntity: ChatRoomEntity)
}