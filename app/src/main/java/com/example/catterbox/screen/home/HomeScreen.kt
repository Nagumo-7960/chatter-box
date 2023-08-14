package com.example.catterbox.screen.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catterbox.ChatApplication
import com.example.catterbox.database.ChatDatabase
import com.example.catterbox.screen.navigation.AppScreen
import com.example.catterbox.ui.theme.CatterBoxTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(toChatRoom: () -> Unit, homeViewModel: HomeViewModel) {
    val allUsers by homeViewModel.allUsers.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                allUsers.forEach{
                    Text(text = it.name)
                }
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                    Button(onClick = { Logout() }) {
                        Text(text = "ログアウト")
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(),
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

fun Logout(){
    GlobalScope.launch {
        ChatApplication.userDao.deleteAll()
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


