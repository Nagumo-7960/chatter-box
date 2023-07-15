package com.example.catterbox

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.ui.theme.CatterBoxTheme

var myTextMessage = mutableListOf<String>("-- 佐藤和弘さんが入室しました --","こんにちは", "-- 坂本庄司さんが入室しました --")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatRoomScreen(toHome: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }
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
                Button(onClick = { toHome()}) {
                    Text(text = "退室")
                }
            }
            
            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                myTextMessage.forEach(){
                    //　TODO 見た目上のレイアウトとして入室時を文字列検索のif文にしているが、データベース設計に合わせて変更する
                    if(it.startsWith("--") && it.endsWith("--")){
                        Column() {
                            Text(text = it)
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }else{
                        Column() {
                            Text(text = it,modifier = Modifier.background(color = Color(0xFF96F3FF),shape = RoundedCornerShape(50))
                                .padding(8.dp))
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
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
                    myTextMessage.add(text)
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
    CatterBoxTheme {
        ChatRoomScreen{
            navController.navigate("home")
        }
    }
}