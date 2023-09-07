package com.example.catterbox.screen.chatroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication
import com.example.catterbox.database.dao.UserDAO
import com.example.catterbox.database.model.MessageEntity
import com.example.catterbox.database.model.UserEntity
import com.example.catterbox.firestore.FireStoreHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatRoomViewModel : ViewModel() {
    private val userDAO: UserDAO = ChatApplication.chatDatabase.userDao()
    private val _user = MutableStateFlow<List<UserEntity>>(emptyList())
    private val _messageList = MutableStateFlow<List<String>>(emptyList())

    val user: StateFlow<List<UserEntity>> get() = _user
    val messageList: StateFlow<List<String>> = _messageList

    init {
        // Update _allNotes with the latest data from the database
        viewModelScope.launch {
            userDAO.getAll().collect { user ->
                _user.value = user
            }
        }
        fetchMessages()
    }

    fun sendMessage(messageContent: String, context: Context) {
        val message = MessageEntity(
            id = 0,
            post_user_id = user.value.first().id,
            message_content = messageContent,
            created_at = System.currentTimeMillis(),
            room_id = 0
        )
        FireStoreHelper().saveUserData(message, context)
    }

    fun fetchMessages() {
        FireStoreHelper().fetchMessagesFromFirestore { messages ->
            _messageList.value = messages
        }
    }
}