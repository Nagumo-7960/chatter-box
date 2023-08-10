package com.example.catterbox

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.database.model.MessageEntity
import com.example.catterbox.ui.theme.CatterBoxTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val isLoggedInState = MutableStateFlow(false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatterBoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(navController = navController,homeViewModel = HomeViewModel())
                }
            }
        }

    }


    fun createDummyData(){
        for (i in 1..5){
            GlobalScope.launch {
                val messageSample = MessageEntity(
                    id = 0,
                    post_user_id = 1,
                    message_content = "やっほーい(${i})",
                    room_id = 0
                )
                ChatApplication.messageDao.create(messageSample)
            }
        }
    }
}

