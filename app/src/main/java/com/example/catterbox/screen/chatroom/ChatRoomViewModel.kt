package com.example.catterbox.screen.chatroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication
import com.example.catterbox.ChatApplication.Companion.messageDao
import com.example.catterbox.database.dao.MessageDAO
import com.example.catterbox.database.dao.UserDAO
import com.example.catterbox.database.model.MessageEntity
import com.example.catterbox.database.model.UserEntity
import com.example.catterbox.firestore.FireStoreHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatRoomViewModel: ViewModel() {
    private val messageDAO: MessageDAO = ChatApplication.chatDatabase.messageDao()
    private val _allMessages = MutableStateFlow<List<MessageEntity>>(emptyList())
    private val userDAO: UserDAO = ChatApplication.chatDatabase.userDao()
    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())

    val allMessages: StateFlow<List<MessageEntity>> get() = _allMessages
    val users: StateFlow<List<UserEntity>> get() = _users

    init {
        // Update _allNotes with the latest data from the database
        viewModelScope.launch {
            messageDao.getAll().collect { messages ->
                _allMessages.value = messages
            }
        }
        viewModelScope.launch {
            userDAO.getAll().collect { user ->
                _users.value = user
            }
        }
    }

    fun insert(note: MessageEntity) = viewModelScope.launch {
        messageDAO.create(note)
    }

    fun delete(note: MessageEntity) = viewModelScope.launch {
        messageDAO.delete(note)
    }

    fun deleteAll() = viewModelScope.launch {
        messageDAO.deleteAll()
    }

    fun insertAndSaveMessage(messageContent: String, context: Context) {
        viewModelScope.launch {
            val newMessage = MessageEntity(
                id = 0,
                post_user_id = users.value.first().id,
                message_content = messageContent,
                room_id = 0
            )
            messageDAO.create(newMessage)

            // メッセージが追加された後にfireStoreにデータを保存
            FireStoreHelper().saveUserData(allMessages.value.first(), context)
        }
    }
}