package com.example.catterbox

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                toChatRoom = { navController.navigate("room")}
            )
        }
        composable("room") {
            ChatRoomScreen()
        }
    }

}