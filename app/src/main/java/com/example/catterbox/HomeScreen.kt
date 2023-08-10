package com.example.catterbox

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.ChatApplication.Companion.userDao
import com.example.catterbox.database.model.UserEntity
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
    Log.d("allusers_home", homeViewModel.allUsers.collectAsState().value.toString())
}

fun Logout(){
    GlobalScope.launch {
        ChatApplication.userDao.deleteAll()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val navController = rememberNavController()
//    CatterBoxTheme {
//        HomeScreen{
//            navController.navigate("home")
//        }
//    }
//}