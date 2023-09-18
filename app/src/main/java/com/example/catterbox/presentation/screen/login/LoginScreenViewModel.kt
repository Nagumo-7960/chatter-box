package com.example.catterbox.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catterbox.ChatApplication
import com.example.catterbox.data.database.dao.UserDAO
import com.example.catterbox.data.database.model.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoginViewModel : ViewModel() {
    private val userDAO: UserDAO = ChatApplication.chatDatabase.userDao()
    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())

    val users: StateFlow<List<UserEntity>> get() = _users

    init {
        // Update _allNotes with the latest data from the database
        viewModelScope.launch {
            userDAO.getAll().collect { user ->
                _users.value = user
            }
        }
    }

    fun insert(userId: String, userName: String) {
        viewModelScope.launch {
            val user = UserEntity(
                id = userId,
                name = userName
            )
            userDAO.create(user)
        }
    }

    fun generateUserId(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..20)
            .map { charset[Random.nextInt(0, charset.length)] }
            .joinToString("")
    }
}