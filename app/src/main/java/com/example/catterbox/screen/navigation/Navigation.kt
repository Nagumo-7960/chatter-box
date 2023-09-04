package com.example.catterbox.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.catterbox.screen.chatroom.ChatRoomScreen
import com.example.catterbox.screen.chatroom.ChatRoomViewModel
import com.example.catterbox.screen.home.HomeScreen
import com.example.catterbox.screen.home.HomeViewModel
import com.example.catterbox.screen.login.LoginScreen
import com.example.catterbox.screen.login.LoginViewModel

@Composable
fun Navigation(navController: NavHostController,homeViewModel: HomeViewModel) {
    val allUsers by homeViewModel.allUsers.collectAsState()
    NavHost(
        navController = navController,
        startDestination = if(allUsers.isNotEmpty()) AppScreen.Home.route else AppScreen.Login.route
    ) {
        composable(AppScreen.Login.route) {
            LoginScreen(
                toHome = { navController.navigate(AppScreen.Home.route) },
                loginViewModel = LoginViewModel()
            )
        }
        composable(AppScreen.Home.route) {
            HomeScreen(
                toChatRoom = { navController.navigate(AppScreen.Room.route) },
                homeViewModel = HomeViewModel()
            )
        }
        composable(AppScreen.Room.route) {
            ChatRoomScreen(
                toHome = { navController.navigate(AppScreen.Home.route) },
                chatViewModel = ChatRoomViewModel()
            )
        }
    }

}