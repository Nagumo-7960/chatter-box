package com.example.catterbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.database.ChatDatabase
import com.example.catterbox.database.dao.MessageDAO
import com.example.catterbox.database.model.MessageEntity
import com.example.catterbox.ui.theme.CatterBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDummyData()
        setContent {
            CatterBoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(navController = navController)
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
                ChatApplication.dao.create(messageSample)
            }
        }
    }
}

