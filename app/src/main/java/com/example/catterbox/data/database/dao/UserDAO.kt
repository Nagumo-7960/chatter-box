package com.example.catterbox.data.database.dao

import androidx.room.*
import com.example.catterbox.data.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("select * from UserEntity order by id desc")
    fun getAll(): Flow<List<UserEntity>>

    //UIスレッドで実行できないためsuspend関数にする
    @Insert
    suspend fun create(userEntity : UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}