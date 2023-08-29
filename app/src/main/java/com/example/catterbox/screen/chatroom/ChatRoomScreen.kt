package com.example.catterbox.screen.chatroom

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.catterbox.ChatApplication
import com.example.catterbox.ChatApplication.Companion.messageDao
import com.example.catterbox.data.MessageData
import com.example.catterbox.database.ChatDatabase
import com.example.catterbox.database.model.MessageEntity
import com.example.catterbox.firestore.FireStoreHelper
import com.example.catterbox.ui.theme.CatterBoxTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatRoomScreen(toHome: () -> Unit, chatViewModel: ChatRoomViewModel) {
    val allMessages by chatViewModel.allMessages.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }
    val fireStoreHelper = FireStoreHelper()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Row() {
                    Button(onClick = { toHome() }) {
                        Text(text = "退室")
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Button(onClick = {
                        GlobalScope.launch {
                            messageDao.deleteAll()
                        }
                    }) {
                        Text(text = "メッセージ全削除")
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                item{
                    allMessages.asReversed().forEach {
                        Column (modifier = Modifier.clickable {
                            //クリックでデータベースから削除
                            GlobalScope.launch {
                                messageDao.delete(messageEntity = it)
                            }
                        }){
                            if(it.post_user_id==2){
                                Text(
                                    text = it.message_content,
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xFF9BFF9F),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(8.dp)
                                )
                            }else{
                                Text(
                                    text = it.message_content,
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xFF96F3FF),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomStart),
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    GlobalScope.launch {
                        val messageSample = MessageEntity(
                            id = 0,
                            post_user_id = 2,
                            message_content = text,
                            room_id = 0
                        )
                        messageDao.create(messageSample)
                        val message = MessageData(
                            post_user_id = 0,
                            message_content = text,
                            room_id = 0
                        )
                        fireStoreHelper.saveUserData(message, context)
                    }
                    Log.d("print_text", text)
                    text = ""

                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(color = LightGray, shape = RoundedCornerShape(50))
                    .padding(top = 8.dp, start = 16.dp)
            )

        }
    }
}

@Preview
@Composable
fun PreviewChatRoomScreen() {
    val navController = rememberNavController()
    ChatApplication.chatDatabase = Room.databaseBuilder(
        LocalContext.current,
        ChatDatabase::class.java,
        "chat-database"
    ).build()
    CatterBoxTheme {
        ChatRoomScreen(
            toHome = { navController.navigate("home") },
            chatViewModel = ChatRoomViewModel()
        )
    }
}