package com.example.catterbox.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication
import com.example.catterbox.database.dao.UserDAO
import com.example.catterbox.database.model.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val userDAO: UserDAO = ChatApplication.chatDatabase.userDao()
    private val _user = MutableStateFlow<List<UserEntity>>(emptyList())
    val user: StateFlow<List<UserEntity>> get() = _user

    init {
        // Update _allNotes with the latest data from the database
        viewModelScope.launch {
            ChatApplication.userDao.getAll().collect { users ->
                _user.value = users
            }
        }
    }

    fun delete(user: UserEntity) = viewModelScope.launch {
        userDAO.delete(user)
    }
}