package com.example.catterbox.presentation.screen.chatroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication
import com.example.catterbox.data.database.dao.MessageDAO
import com.example.catterbox.data.database.dao.UserDAO
import com.example.catterbox.data.database.model.MessageEntity
import com.example.catterbox.data.database.model.UserEntity
import com.example.catterbox.data.firestore.FireStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val userDAO: UserDAO
): ViewModel() {
    private val _user = MutableStateFlow<List<UserEntity>>(emptyList())
    val user: StateFlow<List<UserEntity>> = _user
    private val _messageList = MutableStateFlow<List<String>>(emptyList())
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