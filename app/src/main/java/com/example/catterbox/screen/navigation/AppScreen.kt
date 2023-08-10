package com.example.catterbox.screen.navigation

sealed class AppScreen (val route:String){
    object Login : AppScreen("login")
    object Home : AppScreen("home")
    object Room : AppScreen("room")
}