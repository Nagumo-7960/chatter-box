package com.example.catterbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication.Companion.messageDao
import com.example.catterbox.database.dao.MessageDAO
import com.example.catterbox.database.model.MessageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatRoomViewModel: ViewModel() {
    private val messageDAO: MessageDAO = ChatApplication.chatDatabase.messageDao()
    private val _allMessages = MutableStateFlow<List<MessageEntity>>(emptyList())

    val allMessages: StateFlow<List<MessageEntity>> get() = _allMessages

    init {
        // Update _allNotes with the latest data from the database
        viewModelScope.launch {
            messageDao.getAll().collect { notes ->
                _allMessages.value = notes
            }
        }
    }

    fun insert(note: MessageEntity) = viewModelScope.launch {
        messageDAO.create(note)
    }

    fun delete(note: MessageEntity) = viewModelScope.launch {
        messageDAO.delete(note)
    }
}