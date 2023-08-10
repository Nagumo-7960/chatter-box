package com.example.catterbox

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController,homeViewModel: HomeViewModel) {
    val allUsers by homeViewModel.allUsers.collectAsState()
    NavHost(navController = navController, startDestination = if(allUsers.isNotEmpty()) "home" else "login") {
        composable("login") {
            LoginScreen(
                toHome = { navController.navigate("home") },
            )
        }
        composable("home") {
            HomeScreen(
                toChatRoom = { navController.navigate("room") },
                homeViewModel = HomeViewModel()
            )
        }
        composable("room") {
            ChatRoomScreen(
                toHome = { navController.navigate("home") },
                chatViewModel = ChatRoomViewModel()
            )
        }
    }

}