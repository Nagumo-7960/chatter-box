package com.example.catterbox

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = if(isLoggedIn()) "home" else "login") {
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