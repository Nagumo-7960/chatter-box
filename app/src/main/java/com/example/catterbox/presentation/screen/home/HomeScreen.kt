package com.example.catterbox.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.catterbox.ChatApplication
import com.example.catterbox.data.database.ChatDatabase
import com.example.catterbox.presentation.screen.navigation.AppScreen
import com.example.catterbox.presentation.screen.ui.theme.CatterBoxTheme

@Composable
fun HomeScreen(toChatRoom: () -> Unit, homeViewModel: HomeViewModel) {
    val user by homeViewModel.user.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                user.forEach {
                    Text(text = it.name)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        homeViewModel.delete(user.first())
                    }) {
                        Text(text = "ログアウト")
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "だれでもルーム")
                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Button(onClick = { toChatRoom() }) {
                        Text(text = "入室する")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    ChatApplication.chatDatabase = Room.databaseBuilder(
        LocalContext.current,
        ChatDatabase::class.java,
        "chat-database"
    ).build()
    CatterBoxTheme {
        HomeScreen(
            toChatRoom = { navController.navigate(AppScreen.Room.route) },
            homeViewModel = HomeViewModel()
        )
    }
}


