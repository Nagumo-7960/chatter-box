package com.example.catterbox.screen.chatroom

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
import com.example.catterbox.database.ChatDatabase
import com.example.catterbox.ui.theme.CatterBoxTheme

@Composable
fun ChatRoomScreen(toHome: () -> Unit, chatViewModel: ChatRoomViewModel) {
    val allMessages by chatViewModel.allMessages.collectAsState()
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Row {
                    Button(onClick = { toHome() }) {
                        Text(text = "退室")
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Button(onClick = {
                        chatViewModel.deleteAll()
                    }) {
                        Text(text = "メッセージ全削除")
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp)
            ) {
                item {
                    allMessages.asReversed().forEach {
                        Column(modifier = Modifier.clickable {
                            //クリックでデータベースから削除
                            chatViewModel.delete(it)
                        }) {
                            if (it.post_user_id == "") {
                                Text(
                                    text = it.message_content, modifier = Modifier
                                        .background(
                                            color = Color(0xFF9BFF9F),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(8.dp)
                                )
                            } else {
                                Text(
                                    text = it.message_content, modifier = Modifier
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
            inputTextField(
                text = text,
                onValueChange = { newText -> text = newText },
                onImeAction = {
                    chatViewModel.insertAndSaveMessage(text, context)
                    text = ""
                }
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun inputTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onImeAction()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(color = LightGray, shape = RoundedCornerShape(50))
            .padding(top = 8.dp, start = 16.dp)
    )
}

@Preview
@Composable
fun PreviewChatRoomScreen() {
    val navController = rememberNavController()
    ChatApplication.chatDatabase = Room.databaseBuilder(
        LocalContext.current, ChatDatabase::class.java, "chat-database"
    ).build()
    CatterBoxTheme {
        ChatRoomScreen(
            toHome = { navController.navigate("home") }, chatViewModel = ChatRoomViewModel()
        )
    }
}